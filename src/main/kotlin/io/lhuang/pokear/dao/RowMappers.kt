package io.lhuang.pokear.dao

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
        Pokemon(
                rs.getLong("pokemon.id"),
                POKEDEX[rs.getLong("pokemon.pokedex_id").toInt()] ?: error("No pokedex entry"),
                rs.getStringOrNull("pokemon.nickname"),
                rs.getInt("pokemon.hp"),
                rs.getInt("pokemon.exp"),
                rs.getInt("pokemon.bond_exp"),
                userRowMapper.mapRow(resultSet, rowNum)
        )
    }

    val userRowMapper = RowMapper { rs, _ ->
        val resultSet = NamespacedResultSet(rs)

        UserModel(
                resultSet.getLong("users.id"),
                resultSet.getString("users.oauth_name"),
                resultSet.getString("users.username"),
                resultSet.getInt("users.money")
        )
    }
}