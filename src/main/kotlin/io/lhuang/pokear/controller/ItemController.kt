package io.lhuang.pokear.controller

import io.lhuang.pokear.item.Bag
import io.lhuang.pokear.manager.ItemManager
import io.lhuang.pokear.manager.UserManager
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/items")
class ItemController(
        val userManager: UserManager,
        val itemManager: ItemManager
) {
    @GetMapping
    fun getItems(
            @AuthenticationPrincipal principal: Principal
    ): Bag {
        val user = getUserOrThrow(userManager, principal)

        return itemManager.getItems(user)
    }
}
