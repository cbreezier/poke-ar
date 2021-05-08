package io.lhuang.pokear.dao

import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.model.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component

@Component
class UserDao(
        @Autowired val jdbcTemplate: NamedParameterJdbcTemplate,
        @Autowired val rowMappers: RowMappers
) {
    fun getUser(id: Long): UserModel? {
        return try {
            jdbcTemplate.queryForObject(
                    "select * from users where id = :id",
                    mapOf("id" to id),
                    rowMappers.userRowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun getUserByOauthName(oAuthName: String): UserModel? {
        return try {
            jdbcTemplate.queryForObject(
                    "select * from users where oauth_name = :oauth_name",
                    mapOf("oauth_name" to oAuthName),
                    rowMappers.userRowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun registerUser(userRegistration: UserRegistration, oAuthName: String): UserModel? {
        val generatedKeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
                "insert into users(oauth_name, username) values(:oauth_name, :username)",
                MapSqlParameterSource(mapOf(
                        "oauth_name" to oAuthName,
                        "username" to userRegistration.username
                )),
                generatedKeyHolder,
                arrayOf("id")
        )

        return generatedKeyHolder.key?.toLong()?.let {
            getUser(it)
        }
    }
}
