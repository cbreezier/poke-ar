package io.lhuang.pokear.battle

import io.lhuang.pokear.manager.ItemManager
import org.springframework.stereotype.Component

@Component
class BattleService(
        val itemManager: ItemManager,
        val battleDao: BattleDao
) {
    fun attack(attackerId: Long, defenderId: Long): BattleResult {
        // TODO actually do something here
        return BattleResult(
                100,
                100,
                true
        )
    }
}
