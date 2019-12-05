package io.lhuang.pokear.pokedex

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PokedexRowMapper : RowMapper<Pokedex> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Pokedex? {
        val id = rs.getLong("id")
        val name = rs.getString("name")

        return Pokedex(id, name)
    }
}
