package io.lhuang.pokear

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonResource(
        val pokemonDao: PokemonDao
) {

    @GetMapping("/pokemon")
    fun getPokemon(): List<Pokemon> {
        return pokemonDao.getPokemon()
    }
}
