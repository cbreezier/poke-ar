package io.lhuang.pokear

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class PokemonDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonRowMapper: PokemonRowMapper
) {

    fun getPokemon(): List<Pokemon> {
        return jdbcTemplate.query("select id, name from pokemon", pokemonRowMapper)
    }

    fun getPokemon(habitat: Habitat): List<Pokemon> {
        return jdbcTemplate.query("select p.id, p.name from habitats h join pokemon p on h.pokemon_id = p.id where h.habitat_type = '" + habitat.name + "'", pokemonRowMapper)
    }
}
