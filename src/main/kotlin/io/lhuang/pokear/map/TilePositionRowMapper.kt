package io.lhuang.pokear.map

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class TilePositionRowMapper : RowMapper<TilePosition> {
    override fun mapRow(rs: ResultSet, rowNum: Int): TilePosition? {
        return TilePosition(
                rs.getInt("tile_x"),
                rs.getInt("tile_y")
        )
    }
}
