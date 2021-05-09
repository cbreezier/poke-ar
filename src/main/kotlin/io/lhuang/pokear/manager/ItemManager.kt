package io.lhuang.pokear.manager

import io.lhuang.pokear.dao.ItemDao
import io.lhuang.pokear.item.Bag
import io.lhuang.pokear.item.Item
import io.lhuang.pokear.item.PokeballType
import io.lhuang.pokear.model.UserModel
import org.springframework.stereotype.Component

@Component
class ItemManager(
        val itemDao: ItemDao
) {
    fun getItem(user: UserModel, itemType: String): Item? {
        return itemDao.getItem(user, itemType)
    }

    fun getItems(user: UserModel): Bag {
        return Bag(itemDao.getItems(user))
    }

    fun addItem(user: UserModel, item: Item): Item {
        return itemDao.addItem(user, item) ?: Item(item.type, 0)
    }

    fun hasItem(user: UserModel, pokeballType: PokeballType): Boolean {
        return getItem(user, pokeballType.name) != null
    }

    fun removeItem(user: UserModel, item: Item) {
        itemDao.removeItem(user, item)
    }
}
