package io.lhuang.pokear.pokemon

import io.lhuang.pokear.pokedex.Pokedex

data class Pokemon(
        val pokedex: Pokedex,
        val hp: Int,
        val level: Int
)
