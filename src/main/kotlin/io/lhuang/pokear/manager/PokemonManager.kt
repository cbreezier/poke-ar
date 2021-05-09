package io.lhuang.pokear.manager

import io.lhuang.pokear.dao.PokemonDao
import io.lhuang.pokear.item.Item
import io.lhuang.pokear.item.PokeballType
import io.lhuang.pokear.model.PokemonModel
import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.pokemon.Pokemon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PokemonManager(
        @Autowired val itemManager: ItemManager,
        @Autowired val pokemonDao: PokemonDao
) {
    fun getPokemon(id: Long): Pokemon? {
        return pokemonDao.getPokemon(id)
    }

    fun getPokemonByOwner(user: UserModel): List<Pokemon> {
        return pokemonDao.getPokemonByOwner(user)
    }

    fun catchPokemon(user: UserModel, pokeballType: PokeballType, pokemonId: Long): Boolean {
        val pokemon = pokemonDao.getPokemon(pokemonId)?.let { PokemonModel.fromPokemon(it) } ?: return false

        if (pokemon.level > pokeballType.maxLevel) {
            return false
        }

        // Check if they have the pokeball
        if (!itemManager.hasItem(user, pokeballType)) {
            return false
        }

        // Spend the pokeball
        itemManager.removeItem(user, Item(pokeballType.displayName, 1))

        // Catch the pokemon
        // TODO calculate chance
        return pokemonDao.catchPokemon(user, pokemonId, pokeballType.bondExpBonus)?.let {
            return it.owner?.id == user.id
        } ?: false
    }
}