package io.lhuang.pokear.dao

import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.pokemon.Pokemon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class PokemonDao(
        @Autowired val jdbcTemplate: NamedParameterJdbcTemplate,
        @Autowired val rowMappers: RowMappers
) {
    fun getPokemon(id: Long): Pokemon? {
        return try {
            jdbcTemplate.queryForObject(
                    "select * from pokemon p join users u on u.id = p.owner_id where p.id = :id",
                    mapOf("id" to id),
                    rowMappers.pokemonRowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun catchPokemon(user: UserModel, id: Long): Pokemon? {
        jdbcTemplate.update(
                "update pokemon set owner_id = :owner_id where id = :id",
                mapOf(
                        "id" to id,
                        "owner_id" to user.id
                )
        )

        return getPokemon(id)
    }
}
