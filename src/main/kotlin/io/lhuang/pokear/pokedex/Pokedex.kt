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
    public fun hpAt(level: Int): Int {
        return interpolateStats(baseHp, level)
    }

    public fun atkAt(level: Int): Int {
        return interpolateStats(baseAtk, level)
    }

    public fun defAt(level: Int): Int {
        return interpolateStats(baseDef, level)
    }

    public fun spAtkAt(level: Int): Int {
        return interpolateStats(baseSpAtk, level)
    }

    public fun spDefAt(level: Int): Int {
        return interpolateStats(baseSpDef, level)
    }

    public fun spdAt(level: Int): Int {
        return interpolateStats(baseSpd, level)
    }

    private fun interpolateStats(stat: Int, level: Int): Int {
        return (stat * (level.toDouble() / 100)).toInt()
    }
}
