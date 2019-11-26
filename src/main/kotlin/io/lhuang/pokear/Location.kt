package io.lhuang.pokear

data class Location(
        val latitude: Double,
        val longitude: Double,
        val terrain: Terrain?,
        val habitat: Habitat?,
        val spawns: List<Pokemon>
)
