package io.lhuang.pokear.model

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.user.User

/**
 * The model to be serialized and returned to a client.
 *
 * Contains all of the final derived/calculated values instead of raw data.
 */
data class PokemonModel(
        val id: Long,
        val pokedex: Pokedex,
        val nickname: String?,

        val level: Int,
        val exp: Int,
        val bondExp: Int,

        val currentHp: Int,

        val hp: Int,
        val atk: Int,
        val def: Int,
        val spAtk: Int,
        val spDef: Int,
        val spd: Int,

        val owner: User?
) {

    companion object {
        public fun fromPokemon(pokemon: Pokemon) : PokemonModel {
            val pokedex = pokemon.pokedex
            val level = pokedex.growthType.expToLevel(pokemon.exp)
            val exp = pokemon.exp - pokedex.growthType.levelToExp(level)

            return PokemonModel(
                    pokemon.id,
                    pokedex,
                    pokemon.nickname,

                    level,
                    exp,
                    pokemon.bondExp,

                    pokemon.hp,

                    pokedex.hpAt(level),
                    pokedex.atkAt(level),
                    pokedex.defAt(level),
                    pokedex.spAtkAt(level),
                    pokedex.spDefAt(level),
                    pokedex.spdAt(level),

                    pokemon.owner
            )
        }
    }
}
