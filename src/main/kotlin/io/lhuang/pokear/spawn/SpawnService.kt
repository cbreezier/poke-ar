package io.lhuang.pokear.spawn

import com.google.maps.model.LatLng
import io.lhuang.pokear.habitat.HabitatService
import io.lhuang.pokear.map.MapPoint
import io.lhuang.pokear.map.MapService
import io.lhuang.pokear.map.MercatorProjection
import io.lhuang.pokear.map.MercatorProjection.Companion.latLngToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToTilePosition
import io.lhuang.pokear.map.TilePosition
import io.lhuang.pokear.pokedex.PokedexDao
import io.lhuang.pokear.pokedex.PokedexManager
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.pokemon.PokemonSpawn
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import kotlin.random.Random


@Component
class SpawnService(
        val mapService: MapService,
        val habitatService: HabitatService,
        val pokedexManager: PokedexManager,
        val spawnDao: SpawnDao
) {
    companion object {
        const val SPAWN_FREQUENCY_MINUTES = 1L
        val SPAWN_FREQUENCY = Duration.ofMinutes(SPAWN_FREQUENCY_MINUTES)

        // The width and height of a single map tile in terms of world coordinates
        // Since the usable world coordinate space is {0, 0} to {256, 256}, a value of
        // 0.0078125 gives us 32768 divisions, which is 2^15
        //
        // This represents a single tile width at zoom level 15.
        //
        // https://developers.google.com/maps/documentation/javascript/coordinates
        private const val TILE_WIDTH = 0.0078125
        private const val TILE_HEIGHT = 0.0078125
    }

    fun cleanupSpawns() {
        spawnDao.cleanupSpawns(Instant.now())
    }

    fun getSpawns(center: LatLng): List<PokemonSpawn> {
        return spawnDao.getSpawns(latLngToWorldPoint(center), TILE_WIDTH, TILE_HEIGHT, Instant.now())
    }

    fun spawnPokemon(num: Int) {
        spawnDao.getVisitedLocations()
                .forEach { spawnPokemon(it, num) }
    }

    fun spawnPokemon(position: TilePosition, num: Int) {
        val map = mapService.getMap(position)

        (0..num).toList()
                .forEach {
                    val mapPoint = MapPoint(
                            Random.nextInt(0, 512),
                            Random.nextInt(0, 512)
                    ) // TODO hardcoded 512 needs to go somewhere

                    val spawnPoints = habitatService.calculateHabitat(map, mapPoint)
                            .flatMap { pokedexManager.getPokedexSpawnStats(it) }

                    val pokedex = weightedRandomBy(spawnPoints) { it.spawnChance }?.pokedex
                    val startTime = Instant.now().plus(jitter(SPAWN_FREQUENCY))
                    val endTime = startTime.plus(SPAWN_FREQUENCY)
                    val worldPoint = MercatorProjection.mapPointToWorldPoint(map, mapPoint)

                    if (pokedex != null) {
                        val pokemon = Pokemon(
                                null,
                                pokedex,
                                100, // TODO
                                Random.nextInt(100), // TODO
                                Random.nextInt(100), // TODO
                                null
                        )
                        spawnDao.addSpawn(worldPoint, pokemon, startTime, endTime)
                    }
                }
    }

    fun visitLocation(location: LatLng) {
        val worldPoint = latLngToWorldPoint(location)
        val tilePosition = worldPointToTilePosition(worldPoint)

        spawnDao.visitLocation(tilePosition, Instant.now())
    }

    fun getVisitedLocations(): List<LatLng> {
        return spawnDao.getVisitedLocations()
                .map { MercatorProjection.tilePositionToWorldPoint(it) }
                .map { MercatorProjection.worldPointToLatLng(it) }
    }

    private fun <T> weightedRandomBy(inputs: List<T>, by: (T) -> Double): T? {
        if (inputs.isEmpty()) {
            return null
        }

        val total = inputs.fold(0.0, { acc, input -> acc + by.invoke(input) } )
        val factor = if (total > 100) (100.0 / total) else 1.0
        val diceRoll = Random.nextDouble(100.0)

        var cur = 0.0
        for (input in inputs) {
            cur += by.invoke(input) * factor
            if (cur >= diceRoll) {
                return input
            }
        }
        return null
    }

    private fun jitter(duration: Duration): Duration {
        val jitterSeconds = Random.nextDouble() * duration.seconds
        return Duration.ofSeconds(jitterSeconds.toLong())
    }
}
