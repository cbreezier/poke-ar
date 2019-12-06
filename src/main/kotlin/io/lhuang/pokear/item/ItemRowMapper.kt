package io.lhuang.pokear.item

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class ItemRowMapper : RowMapper<Item> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Item? {
        return Item(
                rs.getString("item_type"),
                rs.getInt("qty")
        )
    }
}
