package io.lhuang.pokear.controller

import io.lhuang.pokear.manager.UserManager
import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.model.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/users")
class UserController(
        @Autowired private val userManager: UserManager
) {
    @GetMapping("/me")
    fun me(
            @AuthenticationPrincipal oAuth2User: OAuth2User
    ): UserModel {
        return userManager.getUserByOauthName(oAuth2User.name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/register")
    fun register(
            @AuthenticationPrincipal oAuth2User: OAuth2User,
            @RequestBody userRegistration: UserRegistration
    ): UserModel {
        return userManager.registerUser(userRegistration, oAuth2User.name) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
