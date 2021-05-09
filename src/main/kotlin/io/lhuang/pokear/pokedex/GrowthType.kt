package io.lhuang.pokear.pokedex

import io.lhuang.pokear.util.ExpStatic

enum class GrowthType {
    ERRATIC,
    FLUCTUATING,
    FAST,
    MEDIUM_FAST,
    MEDIUM_SLOW,
    SLOW;

    public fun expToLevel(exp: Int): Int {
        val expTable = getExpTable()

        return expTable.filter { it <= exp }.lastIndex
    }

    public fun levelToExp(level: Int): Int {
        val expTable = getExpTable()

        return expTable[level]
    }

    private fun getExpTable(): Array<Int> {
        return when (this) {
            ERRATIC -> ExpStatic.ERRATIC
            FLUCTUATING -> ExpStatic.FLUCTUATING
            FAST -> ExpStatic.FAST
            MEDIUM_FAST -> ExpStatic.MEDIUM_FAST
            MEDIUM_SLOW -> ExpStatic.MEDIUM_SLOW
            SLOW -> ExpStatic.SLOW
        }
    }
}