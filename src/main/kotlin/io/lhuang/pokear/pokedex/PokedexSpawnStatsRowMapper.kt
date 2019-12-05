package io.lhuang.pokear.pokedex

import io.lhuang.pokear.pokedex.Pokedex
import io.lhuang.pokear.pokedex.PokedexSpawnStats
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PokedexSpawnStatsRowMapper : RowMapper<PokedexSpawnStats> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PokedexSpawnStats? {
        val id = rs.getLong("id")
        val name = rs.getString("name")
        val rarity = rs.getDouble("rarity")

        return PokedexSpawnStats(
                Pokedex(id, name),
                rarity
        )
    }
}
