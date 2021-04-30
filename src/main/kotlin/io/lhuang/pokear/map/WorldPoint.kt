package io.lhuang.pokear.map

/**
 * Represents a world coordinate in Google Maps.
 *
 * World coordinates in Google Maps are measured from the Mercator projection's origin (the northwest corner of
 * the map at 180 degrees longitude and approximately 85 degrees latitude) and increase in the x direction towards
 * the east (right) and increase in the y direction towards the south (down).
 *
 * https://developers.google.com/maps/documentation/javascript/coordinates
 */
data class WorldPoint(
        val x: Double,
        val y: Double
)
