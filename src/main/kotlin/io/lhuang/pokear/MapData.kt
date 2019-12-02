package io.lhuang.pokear

import com.google.maps.model.LatLng
import java.awt.image.BufferedImage

data class MapData(
        val center: LatLng,
        val width: Int,
        val height: Int,
        val zoom: Int,
        val imageData: BufferedImage,
        val places: Map<PointOfInterest, List<LatLng>>
)
