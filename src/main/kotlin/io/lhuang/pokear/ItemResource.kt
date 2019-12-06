package io.lhuang.pokear

import io.lhuang.pokear.item.Bag
import io.lhuang.pokear.item.Item
import io.lhuang.pokear.item.ItemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemResource(
        val itemService: ItemService
) {
    @GetMapping("/user/{userId}/item")
    fun getItems(@PathVariable("userId") userId: Long): Bag {
        return itemService.getItems(userId)
    }

    @PostMapping("/user/{userId}/item/{itemType}")
    fun addItem(@PathVariable("userId") userId: Long,
                @PathVariable("itemType") itemType: String) {
        itemService.addItem(userId, Item(itemType, 1))
    }
}
