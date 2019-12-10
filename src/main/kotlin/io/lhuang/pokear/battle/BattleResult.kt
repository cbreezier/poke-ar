package io.lhuang.pokear.battle

data class BattleResult(
        val attackerHealth: Int,
        val defenderHealth: Int,
        val attackerWentFirst: Boolean
)
