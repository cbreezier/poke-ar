package io.lhuang.pokear.map

import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.StaticMapsApi
import com.google.maps.StaticMapsRequest
import com.google.maps.model.LatLng
import com.google.maps.model.Size
import io.lhuang.pokear.map.MercatorProjection.Companion.tilePositionToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToLatLng
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

    fun getMap(position: TilePosition): MapTile {
        val worldPoint = tilePositionToWorldPoint(position)
        val centerWorldPoint = WorldPoint(
                worldPoint.x + TILE_WORLD_WIDTH / 2,
                worldPoint.y + TILE_WORLD_WIDTH / 2
        )
        val latLng = worldPointToLatLng(centerWorldPoint)

        // Since we can request a 512x512 tile (which is technically 4 tiles at a given zoom level) we ask for
        // a zoom level of one higher. This effectively gives us a full tile at the correct zoom level, with higher resolution.
        val imageResult = StaticMapsApi.newRequest(apiContext.value, Size(TILE_PIXEL_WIDTH, TILE_PIXEL_WIDTH))
                .center(latLng)
                .zoom(TILE_ZOOM_LEVEL + 1)
                .format(StaticMapsRequest.ImageFormat.png)
                .custom("style", "feature:all|element:labels|visibility:off")
                // Currently the Java api doesn't support multiple custom styles :fistshake:
                //.custom("style", "feature:all|element:geometry|visibility:simplified")
                .await()

        val image = ImageIO.read(ByteArrayInputStream(imageResult.imageData))

        val places = PointOfInterest.values()
                .map { Pair(it, getNearby(latLng, it.searchTerm, 100)) /* TODO how many meters? */ }
                .toMap()

        return MapTile(
                position,
                TILE_ZOOM_LEVEL + 1,
                image,
                places
        )
    }

    fun getNearby(latLng: LatLng, searchTerm: String, radiusMeters: Int): List<LatLng> {
        return emptyList() // disable nearby search for now - too expensive

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
