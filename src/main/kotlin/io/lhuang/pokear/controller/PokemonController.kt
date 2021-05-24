package io.lhuang.pokear.controller

import io.lhuang.pokear.battle.CatchResult
import io.lhuang.pokear.item.PokeballType
import io.lhuang.pokear.manager.PokemonManager
import io.lhuang.pokear.manager.UserManager
import io.lhuang.pokear.model.PokemonModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@RestController
@RequestMapping("/api/v1/pokemon")
class PokemonController(
        @Autowired private val userManager: UserManager,
        @Autowired private val pokemonManager: PokemonManager
) {
    @GetMapping
    fun getPokemon(
            @AuthenticationPrincipal principal: Principal
    ): List<PokemonModel> {
        val user = getUserOrThrow(userManager, principal)

        return pokemonManager.getPokemonByOwner(user).map { PokemonModel.fromPokemon(it) }
    }

    @GetMapping("/{pokemonId}")
    fun getPokemonById(
            @AuthenticationPrincipal principal: Principal,
            @PathVariable("pokemonId") pokemonId: Long
    ): PokemonModel {
        getUserOrThrow(userManager, principal)

        return pokemonManager.getPokemon(pokemonId)?.let {
            PokemonModel.fromPokemon(it)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/{pokemonId}/catch")
    fun catchPokemon(
            @AuthenticationPrincipal principal: Principal,
            @PathVariable("pokemonId") pokemonId: Long,
            @RequestParam("pokeballType") pokeballType: PokeballType
    ): CatchResult {
        val user = getUserOrThrow(userManager, principal)

        val success = pokemonManager.catchPokemon(user, pokeballType, pokemonId)
        return CatchResult(success)
    }
}
