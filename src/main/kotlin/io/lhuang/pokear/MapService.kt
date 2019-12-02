package io.lhuang.pokear

import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.StaticMapsApi
import com.google.maps.StaticMapsRequest
import com.google.maps.model.LatLng
import com.google.maps.model.Size
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
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

    fun getMap(latLng: LatLng): MapData {
        val imageResult = StaticMapsApi.newRequest(apiContext.value, Size(512, 512))
                .center(latLng)
                .zoom(16)
                .format(StaticMapsRequest.ImageFormat.png)
                .custom("style", "feature:all|element:labels|visibility:off")
                // Currently the Java api doesn't support multiple custom styles :fistshake:
                //.custom("style", "feature:all|element:geometry|visibility:simplified")
                .await()

        val image = ImageIO.read(ByteArrayInputStream(imageResult.imageData))

        val places = PointOfInterest.values()
                .map { Pair(it, getNearby(latLng, it.searchTerm, 100)) /* TODO how many meters? */ }
                .toMap()

        return MapData(
                latLng,
                512,
                512,
                16,
                image,
                places
        )
    }

    fun getNearby(latLng: LatLng, searchTerm: String, radiusMeters: Int): List<LatLng> {
        val searchResults = PlacesApi.nearbySearchQuery(apiContext.value, latLng)
                .keyword(searchTerm)
                .radius(radiusMeters)
                .await()
                .results
                .toList()

        return searchResults
                .map { it.geometry.location }
                .toList()
    }
}
