package io.lhuang.pokear.model

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.user.User

data class PokemonModel(
        val id: Long,
        val pokedex: Pokedex,
        val hp: Int,
        val level: Int,
        val exp: Int,
        val bondLevel: Int,
        val bondExp: Int,
        val owner: User?
) {
    constructor(pokemon: Pokemon) : this(
            pokemon.id!!, // TODO how to handle this
            pokemon.pokedex,
            pokemon.hp,
            rawExpToLevel(pokemon.pokedex, pokemon.exp),
            rawExpToExp(pokemon.pokedex, pokemon.exp),
            rawBondExpToBondLevel(pokemon.pokedex, pokemon.bondExp),
            rawBondExpToBondExp(pokemon.pokedex, pokemon.bondExp),
            pokemon.owner
    )

    companion object {
        private fun rawExpToLevel(pokedex: Pokedex, rawExp: Int): Int {
            return rawExp // TODO
        }

        private fun rawExpToExp(pokedex: Pokedex, rawExp: Int): Int {
            return rawExp // TODO
        }

        private fun rawBondExpToBondLevel(pokedex: Pokedex, rawBondExp: Int): Int {
            return rawBondExp // TODO
        }

        private fun rawBondExpToBondExp(pokedex: Pokedex, rawBondExp: Int): Int {
            return rawBondExp // TODO
        }
    }
}
