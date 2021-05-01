package io.lhuang.pokear.controller

import io.lhuang.pokear.item.Bag
import io.lhuang.pokear.item.Item
import io.lhuang.pokear.item.ItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/{userId}/item")
class ItemController(
        val itemService: ItemService
) {
    @GetMapping
    fun getItems(@PathVariable("userId") userId: Long): Bag {
        return itemService.getItems(userId)
    }

    @PostMapping("/{itemType}")
    fun addItem(@PathVariable("userId") userId: Long,
                @PathVariable("itemType") itemType: String) {
        itemService.addItem(userId, Item(itemType, 1))
    }
}
