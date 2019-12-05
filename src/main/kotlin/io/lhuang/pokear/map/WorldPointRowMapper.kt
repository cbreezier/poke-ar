package io.lhuang.pokear.map

import io.lhuang.pokear.map.WorldPoint
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class WorldPointRowMapper : RowMapper<WorldPoint> {
    override fun mapRow(rs: ResultSet, rowNum: Int): WorldPoint? {
        return WorldPoint(
                rs.getDouble("world_x"),
                rs.getDouble("world_y")
        )
    }
}
