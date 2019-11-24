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
}
