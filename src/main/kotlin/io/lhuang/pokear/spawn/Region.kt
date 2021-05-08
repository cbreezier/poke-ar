package io.lhuang.pokear.spawn

import io.lhuang.pokear.pokedex.Type
import io.lhuang.pokear.util.weightedRandomBy
import kotlin.random.Random

data class Region(
        val locality: String,
        val state: String,
        val country: String,
        val minLevel: Int,
        val maxLevel: Int,
        val generation: Int,
        val gymType: Type
) {
    companion object {
        private val levelRarities = mapOf(
                0 to 3,
                10 to 4,
                20 to 5,
                30 to 4,
                40 to 3,
                50 to 3,
                60 to 2,
                70 to 2,
                80 to 1,
                90 to 1
        )

        fun random(locality: String, state: String, country: String): Region {
            // We want a higher proportion of low-level regions than high level ones
            val minLevel = weightedRandomBy(levelRarities.keys.toList()) { (levelRarities[it] ?: error("Missing level rarity")).toDouble() }
            val maxLevel = minLevel + 10

            return Region(
                    locality,
                    state,
                    country,
                    minLevel,
                    maxLevel,
                    Random.nextInt(1, 2),
                    Type.values().random()
            )
        }
    }
}