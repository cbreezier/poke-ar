package io.lhuang.pokear

import java.awt.Color

enum class Terrain(val type: String, val color: Color) {
    GRASS("grass", Color(0xC9EEC9)),
    FOREST("forest", Color(0xB6E2B6)),
    WATER("water", Color(0xAADAFF)),
    MANMADE("manmade", Color(0xF2F3F4)),
    ROAD("road", Color(0xFFFFFF)),
    SAND("sand", Color(0xE8FBD9));

    companion object {
        fun fromColor(color: Color): Terrain? {
            return values()
                    .find { t -> similarColor(t.color, color) }
        }

        private fun similarColor(a: Color, b: Color): Boolean {
            if (!similarInt(a.red, b.red)) return false
            if (!similarInt(a.green, b.green)) return false
            if (!similarInt(a.blue, b.blue)) return false

            return true
        }

        private fun similarInt(a: Int, b: Int): Boolean {
            if (a == b) return true
            if (b < a) {
                return similarInt(b, a)
            }

            val diff = b - a
            return diff * 20 < b
        }
    }
}
