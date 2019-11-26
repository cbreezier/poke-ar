package io.lhuang.pokear

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class PokemonDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonRowMapper: PokemonRowMapper,
        private val pokemonSpawnStatsRowMapper: PokemonSpawnStatsRowMapper
) {

    fun getPokemon(): List<Pokemon> {
        return jdbcTemplate.query("select id, name from pokemon", pokemonRowMapper)
    }

    fun getPokemonSpawns(habitat: Habitat): List<PokemonSpawnStats> {
        return jdbcTemplate.query(
                """
                    SELECT
                        p.id,
                        p.name,
                        h.rarity
                    FROM habitats h
                    JOIN pokemon p ON h.pokemon_id = p.id
                    WHERE h.habitat_type = '${habitat.name}' OR h.habitat_type = 'ANY'
                """.trimIndent(),
                pokemonSpawnStatsRowMapper
        )
    }
}
