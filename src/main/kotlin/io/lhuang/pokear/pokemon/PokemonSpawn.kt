package io.lhuang.pokear.pokemon

import java.time.Instant

data class PokemonSpawn(
        val pokemon: Pokemon,
        val latitude: Double,
        val longitude: Double,
        val startTime: Instant,
        val endTime: Instant
)
