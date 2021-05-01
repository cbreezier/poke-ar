package io.lhuang.pokear.controller

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokedex.PokedexDao
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
        private val pokedexDao: PokedexDao
) {

    @GetMapping("/pokemon")
    fun getPokemon(): List<Pokedex> {
        return pokedexDao.getPokemon()
    }
}
