package io.lhuang.pokear.map

import com.google.maps.*
import com.google.maps.model.AddressComponentType
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.google.maps.model.Size
import io.lhuang.pokear.map.MercatorProjection.Companion.tilePositionToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToLatLng
import io.lhuang.pokear.spawn.RegionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.util.concurrent.ConcurrentHashMap
import javax.imageio.ImageIO
import kotlin.math.cos

@Component
class MapService(
        @Autowired private val regionManager: RegionManager
) {

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

    // TODO in-memory cache is not scalable
    private val mapCache = ConcurrentHashMap<TilePosition, MapTile>()

    fun getMap(position: TilePosition): MapTile {
        return mapCache.getOrPut(position) { loadMap(position) }
    }

    private fun loadMap(position: TilePosition): MapTile {
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

        // https://gis.stackexchange.com/questions/7430/what-ratio-scales-do-google-maps-zoom-levels-correspond-to
        val metersPerPixel = 156543.03392 * cos(latLng.lat * Math.PI / 180) / (1 shl (TILE_ZOOM_LEVEL + 1))
        // In Sydney this is about 500m
        val metersRadius = metersPerPixel * 256

        val places = PointOfInterest.values()
                .map { Pair(it, getNearby(latLng, it.searchTerm, metersRadius.toInt())) }
                .toMap()

        // Get the region of a map tile
        val geocodingResult = GeocodingApi.reverseGeocode(apiContext.value, latLng)
                .await()
        val postcode = getPostcode(geocodingResult)
        val regionName = getRegionName(geocodingResult)
        val country = getCountry(geocodingResult)

        val region = regionManager.getRegion(postcode, regionName, country)

        return MapTile(
                position,
                TILE_ZOOM_LEVEL + 1,
                image,
                places,
                region
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

    private fun getPostcode(geocodingResults: Array<GeocodingResult>): String {
        return geocodingResults.firstOrNull()?.addressComponents
                ?.firstOrNull { it.types.contains(AddressComponentType.POSTAL_CODE) }
                ?.longName
                ?: "unknown"
    }

    private fun getRegionName(geocodingResults: Array<GeocodingResult>): String {
        return geocodingResults.firstOrNull()?.addressComponents
                ?.firstOrNull { it.types.contains(AddressComponentType.LOCALITY) }
                ?.longName
                ?: "unknown"
    }

    private fun getCountry(geocodingResults: Array<GeocodingResult>): String {
        return geocodingResults.firstOrNull()?.addressComponents
                ?.firstOrNull { it.types.contains(AddressComponentType.COUNTRY) }
                ?.longName
                ?: "unknown"
    }
}
