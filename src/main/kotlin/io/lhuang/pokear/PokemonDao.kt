package io.lhuang.pokear

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class PokemonDao(
        val jdbcTemplate: JdbcTemplate,
        val pokemonRowMapper: PokemonRowMapper
) {

    fun getPokemon(): List<Pokemon> {
        return jdbcTemplate.query("select id, name from pokemon", pokemonRowMapper)
    }
}
