package io.lhuang.pokear.map

import com.google.maps.model.LatLng
import java.awt.image.BufferedImage

/**
 * A single "tile" of map data, containing the pixel data of the map tile itself as well as surrounding points
 * of interest.
 */
data class MapData(
        val center: LatLng,
        val width: Int,
        val height: Int,
        val zoom: Int,
        val imageData: BufferedImage,
        val places: Map<PointOfInterest, List<LatLng>>
)
