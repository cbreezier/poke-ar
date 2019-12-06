package io.lhuang.pokear.item

import org.springframework.stereotype.Component

@Component
class ItemService(
        val itemDao: ItemDao
) {
    fun getItems(userId: Long): Bag {
        return Bag(itemDao.getItems(userId))
    }

    fun addItem(userId: Long, item: Item) {
        itemDao.addItem(userId, item)
    }
}
