package io.lhuang.pokear.dao

import io.lhuang.pokear.item.Item
import io.lhuang.pokear.model.UserModel
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class ItemDao(
        val jdbcTemplate: NamedParameterJdbcTemplate,
        val rowMappers: RowMappers
) {
    fun getItem(user: UserModel, itemType: String): Item? {
        return try {
            return jdbcTemplate.queryForObject(
                    "select * from items i where i.owner_id = :owner_id and i.item_type = :item_type",
                    mapOf(
                            "owner_id" to user.id,
                            "item_type" to itemType
                    ),
                    rowMappers.itemRowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun getItems(user: UserModel): List<Item> {
        return jdbcTemplate.query(
                "select * from items i where i.owner_id = :owner_id",
                mapOf("owner_id" to user.id),
                rowMappers.itemRowMapper
        )
    }

    fun addItem(user: UserModel, item: Item): Item? {
        jdbcTemplate.update(
                "insert into items (item_type, qty, owner_id) " +
                        "values (:item_type, :count, :owner_id) " +
                        "on conflict (item_type, owner_id) do update set qty = items.qty + :count",
                mapOf(
                        "item_type" to item.type,
                        "count" to item.count,
                        "owner_id" to user.id
                )
        )

        return getItem(user, item.type)
    }

    fun removeItem(user: UserModel, item: Item): Item? {
        jdbcTemplate.update(
                "update items set qty = items.qty - :count where owner_id = :owner_id and item_type = :item_type",
                mapOf(
                        "count" to item.count,
                        "owner_id" to user.id,
                        "item_type" to item.type
                )
        )

        return getItem(user, item.type)
    }
}
