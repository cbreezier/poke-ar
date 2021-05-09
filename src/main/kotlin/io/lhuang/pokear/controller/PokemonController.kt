package io.lhuang.pokear.controller

import io.lhuang.pokear.battle.CatchResult
import io.lhuang.pokear.item.PokeballType
import io.lhuang.pokear.manager.PokemonManager
import io.lhuang.pokear.manager.UserManager
import io.lhuang.pokear.model.PokemonModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/pokemon")
class PokemonController(
        @Autowired private val userManager: UserManager,
        @Autowired private val pokemonManager: PokemonManager
) {
    @GetMapping
    fun getPokemon(
            @AuthenticationPrincipal oAuth2User: OAuth2User
    ): List<PokemonModel> {
        val user = userManager.getUserByOauthName(oAuth2User.name) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered")

        return pokemonManager.getPokemonByOwner(user).map { PokemonModel.fromPokemon(it) }
    }

    @GetMapping("/{pokemonId}")
    fun getPokemonById(
            @PathVariable("pokemonId") pokemonId: Long
    ): PokemonModel {
        return pokemonManager.getPokemon(pokemonId)?.let {
            PokemonModel.fromPokemon(it)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/{pokemonId}/catch")
    fun catchPokemon(
            @AuthenticationPrincipal oAuth2User: OAuth2User,
            @PathVariable("pokemonId") pokemonId: Long,
            @RequestParam("pokeballType") pokeballType: PokeballType
    ): CatchResult {
        val user = userManager.getUserByOauthName(oAuth2User.name) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered")

        val success = pokemonManager.catchPokemon(user, pokeballType, pokemonId)
        return CatchResult(success)
    }
}
