package io.lhuang.pokear.manager

import io.lhuang.pokear.dao.PokemonDao
import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.pokemon.Pokemon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PokemonManager(
        @Autowired val pokemonDao: PokemonDao
) {
    fun getPokemon(id: Long): Pokemon? {
        return pokemonDao.getPokemon(id)
    }

    fun getPokemonByOwner(user: UserModel): List<Pokemon> {
        return pokemonDao.getPokemonByOwner(user)
    }

    fun catchPokemon(user: UserModel, id: Long): Pokemon? {
        return pokemonDao.catchPokemon(user, id)
    }
}