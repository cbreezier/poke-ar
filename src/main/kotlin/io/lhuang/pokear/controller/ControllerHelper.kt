package io.lhuang.pokear.controller

import io.lhuang.pokear.manager.UserManager
import io.lhuang.pokear.model.UserModel
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

fun getUserOrThrow(userManager: UserManager, principal: Principal): UserModel {
    return userManager.getUserByOauthName(principal.name) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered")
}