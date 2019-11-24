package io.lhuang.pokear

import com.google.maps.GeoApiContext
import com.google.maps.ImageResult
import com.google.maps.StaticMapsApi
import com.google.maps.StaticMapsRequest
import com.google.maps.model.LatLng
import com.google.maps.model.Size
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.awt.Color
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

@Component
class MapService {

    @Value("\${maps.apiKey}")
    private lateinit var apiKey: String

    private val apiContext: Lazy<GeoApiContext>

    init {
        apiContext = lazy {
            GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build()
        }
    }

    fun getMap(latLng: LatLng): ImageResult {
        return StaticMapsApi.newRequest(apiContext.value, Size(512, 512))
                .center(latLng)
                .zoom(16)
                .format(StaticMapsRequest.ImageFormat.png)
                .custom("style", "feature:all|element:labels|visibility:off&style=feature:all|element:geometry|visibility:simplified")
                .await()

    }

    fun getTerrain(latLng: LatLng): Terrain? {
        val image = ImageIO.read(ByteArrayInputStream(getMap(latLng).imageData))

        val pixel = Color(image.getRGB(image.width / 2, image.height / 2))

        return Terrain.fromColor(pixel)
    }
}
