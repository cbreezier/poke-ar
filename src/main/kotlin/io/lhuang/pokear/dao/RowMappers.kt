package io.lhuang.pokear.dao

import io.lhuang.pokear.item.Item
import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.pokedex.POKEDEX
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.util.NamespacedResultSet
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class RowMappers {
    val pokemonRowMapper = RowMapper { resultSet: ResultSet, rowNum: Int ->
        val rs = NamespacedResultSet(resultSet)
        val owner = if (rs.hasColumn("pokemon.owner_id")) {
            userRowMapper.mapRow(resultSet, rowNum)
        } else {
            null
        }

        Pokemon(
                rs.getLong("pokemon.id"),
                POKEDEX[rs.getLong("pokemon.pokedex_id").toInt()] ?: error("No pokedex entry"),
                rs.getStringOrNull("pokemon.nickname"),
                rs.getInt("pokemon.hp"),
                rs.getInt("pokemon.exp"),
                rs.getInt("pokemon.bond_exp"),
                owner
        )
    }

    val userRowMapper = RowMapper { resultSet, _ ->
        val rs = NamespacedResultSet(resultSet)

        UserModel(
                rs.getLong("users.id"),
                rs.getString("users.oauth_name"),
                rs.getString("users.username"),
                rs.getInt("users.money")
        )
    }

    val itemRowMapper = RowMapper { resultSet, _ ->
        val rs = NamespacedResultSet(resultSet)
        Item(
                rs.getString("items.item_type"),
                rs.getInt("items.qty")
        )
    }
}