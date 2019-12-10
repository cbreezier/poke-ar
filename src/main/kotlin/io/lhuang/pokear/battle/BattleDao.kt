package io.lhuang.pokear.battle

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class BattleDao(
        val jdbcTemplate: JdbcTemplate
) {
    fun catchPokemon(ownerId: Long, pokemonId: Long): Boolean {
        val rowsUpdated = jdbcTemplate.update("update pokemon set owner_id = ${ownerId} where id = ${pokemonId}")

        return rowsUpdated == 1
    }
}
