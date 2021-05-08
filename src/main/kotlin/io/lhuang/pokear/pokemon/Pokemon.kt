package io.lhuang.pokear.pokemon

import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.pokedex.Pokedex

data class Pokemon(
        val id: Long,
        val pokedex: Pokedex,
        val nickname: String?,
        val hp: Int,
        val exp: Int,
        val bondExp: Int,
        val owner: UserModel?
)
