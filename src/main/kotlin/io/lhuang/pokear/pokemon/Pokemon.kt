package io.lhuang.pokear.pokemon

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.user.User

data class Pokemon(
        val id: Long?,
        val pokedex: Pokedex,
        val hp: Int,
        val exp: Int,
        val bondExp: Int,
        val owner: User?
)
