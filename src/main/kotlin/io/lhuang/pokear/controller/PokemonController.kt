package io.lhuang.pokear.controller

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokedex.PokedexDao
import io.lhuang.pokear.pokedex.PokedexManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(
        private val pokedexManager: PokedexManager
) {

    @GetMapping
    fun getPokedexEntries(): List<Pokedex> {
        return pokedexManager.getPokedexEntries()
    }
}
