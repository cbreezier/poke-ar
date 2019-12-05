package io.lhuang.pokear.spawn

import io.lhuang.pokear.habitat.Terrain
import io.lhuang.pokear.habitat.Habitat
import io.lhuang.pokear.pokedex.PokedexSpawnStats

data class SpawnPoint(
        val latitude: Double,
        val longitude: Double,
        val terrain: Terrain?,
        val habitats: List<Habitat>,
        val spawns: List<PokedexSpawnStats>
)
