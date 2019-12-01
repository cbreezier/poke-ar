package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.stereotype.Component
import java.util.concurrent.ForkJoinPool
import kotlin.random.Random


@Component
class SpawnService(
        val habitatService: HabitatService,
        val pokemonDao: PokemonDao,
        val spawnDao: SpawnDao
) {

    fun getSpawns(center: LatLng, width: Double, height: Double): List<PokemonSpawn> {
        return spawnDao.getSpawns(center, width, height)
    }

    fun spawnPokemon(center: LatLng, width: Double, height: Double, num: Int) {
        val customThreadPool = ForkJoinPool(num)
        customThreadPool.submit { ->
        (0..num).toList().parallelStream()
                .forEach {
                    val location = LatLng(center.lat + Random.nextDouble() * width - (width / 2), center.lng + Random.nextDouble() * height - (height / 2))
                    val habitat = habitatService.calculateHabitat(location)
                    val spawnPoints = pokemonDao.getPokemonSpawns(habitat)

                    val pokemon = weightedRandomBy(spawnPoints) { it.spawnChance }?.pokemon
                    if (pokemon != null) {
                        spawnDao.addSpawn(location, pokemon)
                    }
                }
        }.join() // TODO spawning should always be done on background threads somehow
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
}
