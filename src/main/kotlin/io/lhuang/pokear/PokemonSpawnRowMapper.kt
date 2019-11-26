package io.lhuang.pokear

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PokemonSpawnRowMapper : RowMapper<PokemonSpawn> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PokemonSpawn? {
        val id = rs.getLong("id")
        val name = rs.getString("name")
        val rarity = rs.getDouble("rarity")

        return PokemonSpawn(
                Pokemon(id, name),
                rarity
        )
    }
}
