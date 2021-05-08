package io.lhuang.pokear.spawn

import io.lhuang.pokear.pokedex.Type
import kotlin.random.Random

data class Region(
        val postcode: String,
        val name: String,
        val country: String,
        val minLevel: Int,
        val maxLevel: Int,
        val generation: Int,
        val gymType: Type
) {
    companion object {
        fun random(postcode: String, name: String, country: String): Region {
            val minLevel = Random.nextInt(0, 10) * 10
            val maxLevel = minLevel + 10
            return Region(
                    postcode,
                    name,
                    country,
                    minLevel,
                    maxLevel,
                    Random.nextInt(1, 2),
                    Type.values().random()
            )
        }
    }
}