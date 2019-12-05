package io.lhuang.pokear.pokedex

import io.lhuang.pokear.habitat.Habitat
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class PokedexDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokedexRowMapper: PokedexRowMapper,
        private val pokedexSpawnStatsRowMapper: PokedexSpawnStatsRowMapper
) {

    fun getPokemon(): List<Pokedex> {
        return jdbcTemplate.query("select id, name from pokedex", pokedexRowMapper)
    }

    fun getPokemonSpawns(habitat: Habitat): List<PokedexSpawnStats> {
        return jdbcTemplate.query(
                """
                    SELECT
                        p.id,
                        p.name,
                        h.rarity
                    FROM habitats h
                    JOIN pokedex p ON h.pokedex_id = p.id
                    WHERE h.habitat_type = '${habitat.name}' OR h.habitat_type = 'ANY'
                """.trimIndent(),
                pokedexSpawnStatsRowMapper
        )
    }
}
