package io.lhuang.pokear.battle

import io.lhuang.pokear.item.Item
import io.lhuang.pokear.item.ItemService
import io.lhuang.pokear.item.PokeballType
import org.springframework.stereotype.Component

@Component
class BattleService(
        val itemService: ItemService,
        val battleDao: BattleDao
) {
    fun attack(attackerId: Long, defenderId: Long): BattleResult {
        // TODO actually do something here
        return BattleResult(
                100,
                100,
                true
        )
    }

    fun catch(pokeballType: PokeballType, ownerId: Long, pokemonId: Long): CatchResult {
        // check if they have the pokeball
        if (!itemService.hasItem(ownerId, pokeballType)) {
            return CatchResult(false)
        }

        // spend the pokeball
        itemService.removeItem(ownerId, Item(pokeballType.displayName, 1))

        // catch the pokemon
        // TODO calculate chance
        if (battleDao.catchPokemon(ownerId, pokemonId)) {
            return CatchResult(true)
        } else {
            return CatchResult(false)
        }
    }
}
