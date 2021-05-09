package io.lhuang.pokear.battle

import org.springframework.web.bind.annotation.RestController

@RestController
class BattleResource(
        val battleService: BattleService
) {
}
