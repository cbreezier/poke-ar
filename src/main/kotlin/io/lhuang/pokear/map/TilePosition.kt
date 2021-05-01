package io.lhuang.pokear.map

/**
 * The integer tile position at our given zoom level. We operate at a hardcoded zoom level of 15, which means there are
 * a total of 2^15 = 32768 tiles in either direction, with {0, 0} being the top left of the world.
 */
data class TilePosition(
        val x: Int,
        val y: Int
)