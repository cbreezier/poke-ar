package io.lhuang.pokear.battle

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class BattleDao(
        val jdbcTemplate: JdbcTemplate
) {
}
