package io.lhuang.pokear.model

import io.lhuang.pokear.pokemon.PokemonSpawn

data class PokemonSpawnModel(
        val id: Long,
        val pokemon: PokemonModel,
        val latitude: Double,
        val longitude: Double
) {
    constructor(pokemonSpawn: PokemonSpawn): this(
            pokemonSpawn.id!!,
            PokemonModel.fromPokemon(pokemonSpawn.pokemon),
            pokemonSpawn.latitude,
            pokemonSpawn.longitude
    )
}
