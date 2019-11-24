package io.lhuang.pokear

import java.awt.Color

enum class Terrain(val type: String, val color: Color) {
    GRASS("grass", Color(0xC9EEC9)),
    WATER("water", Color(0xAADAFF)),
    ROAD("road", Color(0xFFFFFF));

    companion object {
        fun fromColor(color: Color): Terrain? {
            return values()
                    .find { t -> t.color == color }
        }
    }
}
