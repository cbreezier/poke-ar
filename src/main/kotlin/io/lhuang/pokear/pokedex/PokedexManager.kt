package io.lhuang.pokear.pokedex

import io.lhuang.pokear.habitat.Habitat
import org.springframework.stereotype.Component

@Component
class PokedexManager(
        val pokedexDao: PokedexDao
) {
    fun getPokedexEntry(id: Long): Pokedex? {
        return POKEDEX[id.toInt()];
    }

    fun getPokedexEntries(): List<Pokedex> {
        return POKEDEX.values.toList()
    }

    fun getPokedexSpawnStats(habitat: Habitat): List<PokedexSpawnStats> {
        return pokedexDao.getPokemonSpawns(habitat)
    }
}