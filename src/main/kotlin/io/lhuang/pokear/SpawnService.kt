package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class SpawnService(
        val mapService: MapService,
        val habitatService: HabitatService,
        val pokemonDao: PokemonDao,
        val spawnDao: SpawnDao
) {

    fun getSpawns(center: LatLng, width: Double, height: Double): List<PokemonSpawn> {
        return spawnDao.getSpawns(center, width, height)
    }

    fun spawnPokemon(center: LatLng, width: Double, height: Double, num: Int) {
        for (i in 0..num) {
            val location = LatLng(center.lat + Random.nextDouble() * width - (width / 2), center.lng + Random.nextDouble() * height - (height / 2))
            val map = mapService.getMap(location)
            val habitat = habitatService.calculateHabitat(location, map)
            val spawnPoints = pokemonDao.getPokemonSpawns(habitat)

            val pokemon = weightedRandomBy(spawnPoints) { it.spawnChance }?.pokemon
            if (pokemon != null) {
                spawnDao.addSpawn(location, pokemon)
            }
        }
    }

    private fun <T> weightedRandomBy(inputs: List<T>, by: (T) -> Double): T? {
        if (inputs.isEmpty()) {
            return null
        }

        val total = inputs.fold(0.0, { acc, input -> acc + by.invoke(input) } )
        val diceRoll = Random.nextDouble(total)

        var cur = 0.0
        for (input in inputs) {
            cur += by.invoke(input)
            if (cur >= diceRoll) {
                return input
            }
        }
        return null
    }
}
