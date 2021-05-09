package io.lhuang.pokear.pokedex

data class Pokedex(
        val id: Long,
        val name: String,

        val baseHp: Int,
        val baseAtk: Int,
        val baseDef: Int,
        val baseSpAtk: Int,
        val baseSpDef: Int,
        val baseSpd: Int,

        val type1: Type,
        val type2: Type?,

        val growthType: GrowthType,
        val baseExpGranted: Int,
        val captureRate: Int
) {
    fun hpAt(level: Int): Int {
        return interpolateStats(baseHp * 2, level) + level + 10
    }

    fun atkAt(level: Int): Int {
        return interpolateStats(baseAtk * 2, level) + 5
    }

    fun defAt(level: Int): Int {
        return interpolateStats(baseDef * 2, level) + 5
    }

    fun spAtkAt(level: Int): Int {
        return interpolateStats(baseSpAtk * 2, level) + 5
    }

    fun spDefAt(level: Int): Int {
        return interpolateStats(baseSpDef * 2, level) + 5
    }

    fun spdAt(level: Int): Int {
        return interpolateStats(baseSpd * 2, level) + 5
    }

    private fun interpolateStats(stat: Int, level: Int): Int {
        return (stat * (level.toDouble() / 100)).toInt()
    }
}
