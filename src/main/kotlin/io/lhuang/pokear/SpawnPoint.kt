package io.lhuang.pokear

data class SpawnPoint(
        val latitude: Double,
        val longitude: Double,
        val terrain: Terrain?,
        val habitats: List<Habitat>,
        val spawns: List<PokemonSpawnStats>
)
