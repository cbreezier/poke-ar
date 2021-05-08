package io.lhuang.pokear.spawn

import com.google.maps.model.LatLng
import io.lhuang.pokear.habitat.HabitatService
import io.lhuang.pokear.map.*
import io.lhuang.pokear.map.MercatorProjection.Companion.latLngToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToTilePosition
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
    }

    fun cleanupSpawns() {
        spawnDao.cleanupSpawns(Instant.now())
    }

    fun getSpawns(center: LatLng): List<PokemonSpawn> {
        return spawnDao.getSpawns(latLngToWorldPoint(center), TILE_WORLD_WIDTH * 3, TILE_WORLD_WIDTH * 3, Instant.now())
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
                            Random.nextInt(0, TILE_PIXEL_WIDTH),
                            Random.nextInt(0, TILE_PIXEL_WIDTH)
                    )

                    val spawnPoints = habitatService.calculateHabitat(map, mapPoint)
                            .flatMap { pokedexManager.getPokedexSpawnStats(it) }

                    val pokedex = weightedRandomBy(spawnPoints) { it.spawnChance }?.pokedex
                    val startTime = Instant.now().plus(jitter(SPAWN_FREQUENCY))
                    val endTime = startTime.plus(SPAWN_FREQUENCY)
                    val worldPoint = MercatorProjection.mapPointToWorldPoint(map, mapPoint)
                    val level = Random.nextInt(map.region.minLevel, map.region.maxLevel)

                    if (pokedex != null) {
                        val pokemon = Pokemon(
                                0,
                                pokedex,
                                null,
                                pokedex.hpAt(level),
                                pokedex.growthType.levelToExp(level),
                                0,
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
