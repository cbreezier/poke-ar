package io.lhuang.pokear.item

import io.lhuang.pokear.user.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class ItemDao(
        val jdbcTemplate: JdbcTemplate,
        val itemRowMapper: ItemRowMapper
) {
    fun getItems(userId: Long): List<Item> {
        return jdbcTemplate.query("select item_type, qty from items i where i.owner_id = ${userId}", itemRowMapper)
    }

    fun addItem(userId: Long, item: Item) {
        jdbcTemplate.update("insert into items (item_type, qty, owner_id) values ('${item.type}', ${item.count}, ${userId}) on conflict (item_type, owner_id) do update set qty = items.qty + ${item.count}")
    }
}
