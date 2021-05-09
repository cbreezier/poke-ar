package io.lhuang.pokear.controller

import io.lhuang.pokear.item.Bag
import io.lhuang.pokear.manager.ItemManager
import io.lhuang.pokear.manager.UserManager
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/items")
class ItemController(
        val userManager: UserManager,
        val itemManager: ItemManager
) {
    @GetMapping
    fun getItems(
            @AuthenticationPrincipal oAuth2User: OAuth2User
    ): Bag {
        val user = userManager.getUserByOauthName(oAuth2User.name) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered")

        return itemManager.getItems(user)
    }
}
