package io.lhuang.pokear

data class SpawnPoint(
        val latitude: Double,
        val longitude: Double,
        val terrain: Terrain?,
        val habitat: Habitat?,
        val spawns: List<PokemonSpawn>
)
