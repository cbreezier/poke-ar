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
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
        @Autowired private val userManager: UserManager
) {
    @GetMapping("/me")
    fun me(
            @AuthenticationPrincipal principal: Principal
    ): UserModel {
        return getUserOrThrow(userManager, principal)
    }

    @PostMapping("/register")
    fun register(
            @AuthenticationPrincipal principal: Principal,
            @RequestBody userRegistration: UserRegistration
    ): UserModel {
        val user = userManager.getUserByOauthName(principal.name)
        if (user != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "User already registered")
        }

        return userManager.registerUser(userRegistration, principal.name) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
