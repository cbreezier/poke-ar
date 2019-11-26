package io.lhuang.pokear

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PokemonSpawnStatsRowMapper : RowMapper<PokemonSpawnStats> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PokemonSpawnStats? {
        val id = rs.getLong("id")
        val name = rs.getString("name")
        val rarity = rs.getDouble("rarity")

        return PokemonSpawnStats(
                Pokemon(id, name),
                rarity
        )
    }
}
