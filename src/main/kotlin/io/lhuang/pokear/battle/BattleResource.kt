package io.lhuang.pokear.battle

import io.lhuang.pokear.item.PokeballType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BattleResource(
        val battleService: BattleService
) {
    @PostMapping("catch/{pokemonId}")
    fun catchPokemon(
            @PathVariable("pokemonId") pokemonId: Long,
            @RequestParam("pokeballType") pokeballType: PokeballType
    ): CatchResult {
        // TODO auth current user
        return battleService.catch(pokeballType, 1, pokemonId)
    }
}
