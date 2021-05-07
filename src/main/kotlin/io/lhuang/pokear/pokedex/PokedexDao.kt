package io.lhuang.pokear.pokedex

import io.lhuang.pokear.habitat.Habitat
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

@Component
class PokedexDao(
        private val jdbcTemplate: JdbcTemplate
) {

    fun getPokemonSpawns(habitat: Habitat): List<PokedexSpawnStats> {
        return jdbcTemplate.query(
                """
                    SELECT
                        h.pokedex_id,
                        h.rarity
                    FROM habitats h
                    WHERE h.habitat_type = '${habitat.name}' OR h.habitat_type = 'ANY'
                """.trimIndent(),
                pokedexSpawnStatsRowMapper
        )
    }

    companion object {
        private val pokedexSpawnStatsRowMapper = RowMapper { rs, _ ->
            val id = rs.getLong("pokedex_id")
            val rarity = rs.getDouble("rarity")

            PokedexSpawnStats(
                    POKEDEX[id.toInt()] ?: error("No pokedex entry for $id"),
                    rarity
            )
        }
    }
}
