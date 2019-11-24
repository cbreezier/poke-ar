package io.lhuang.pokear

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PokemonRowMapper : RowMapper<Pokemon> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Pokemon? {
        val id = rs.getLong("id")
        val name = rs.getString("name")

        return Pokemon(id, name)
    }
}
