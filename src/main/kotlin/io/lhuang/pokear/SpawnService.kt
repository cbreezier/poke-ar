package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import kotlin.random.Random


@Component
class SpawnService(
        val mapService: MapService,
        val habitatService: HabitatService,
        val pokemonDao: PokemonDao,
        val spawnDao: SpawnDao
) {
    companion object {
        const val SPAWN_FREQUENCY_MINUTES = 1L
        val SPAWN_FREQUENCY = Duration.ofMinutes(SPAWN_FREQUENCY_MINUTES)
        private const val TILE_WIDTH = 0.0078125
        private const val TILE_HEIGHT = 0.0078125
    }

    fun cleanupSpawns() {
        spawnDao.cleanupSpawns(Instant.now())
    }

    fun getSpawns(center: LatLng): List<PokemonSpawn> {
        return spawnDao.getSpawns(MercatorProjection.latLngToWorldPoint(center), TILE_WIDTH, TILE_HEIGHT, Instant.now())
    }

    fun spawnPokemon(center: LatLng, num: Int) {
        val map = mapService.getMap(center)

        (0..num).toList()
                .forEach {
                    val mapPoint = MapPoint(
                            Random.nextInt(0, 512),
                            Random.nextInt(0, 512)
                    ) // TODO hardcoded 512 needs to go somewhere

                    val spawnPoints = habitatService.calculateHabitat(map, mapPoint)
                            .flatMap { pokemonDao.getPokemonSpawns(it) }

                    val pokemon = weightedRandomBy(spawnPoints) { it.spawnChance }?.pokemon
                    val startTime = Instant.now().plus(jitter(SPAWN_FREQUENCY))
                    val endTime = startTime.plus(Duration.ofMinutes(20))
                    val worldPoint = MercatorProjection.mapPointToWorldPoint(map, mapPoint)

                    if (pokemon != null) {
                        spawnDao.addSpawn(worldPoint, pokemon, startTime, endTime)
                    }
                }
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
