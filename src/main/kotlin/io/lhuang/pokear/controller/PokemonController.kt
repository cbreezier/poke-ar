package io.lhuang.pokear.controller

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokedex.PokedexDao
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(
        private val pokedexDao: PokedexDao
) {

    @GetMapping
    fun getPokemon(): List<Pokedex> {
        return pokedexDao.getPokemon()
    }
}
