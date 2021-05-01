package io.lhuang.pokear.map

import com.google.maps.model.LatLng
import java.lang.Math.PI
import kotlin.math.*

/**
 * The world is a globe, but our maps are 2D images.
 *
 * It's easier to work with maps as if the entire world was a big flat rectangle. The Mercator projection
 * is a cylindrical map projection which is a reasonable approximation of a flattened globe.
 *
 * Google maps uses the Mercator projection, so this class helps convert between lat/lng (globe coordinates) to x/y
 * [WorldPoint]s.
 *
 * https://developers.google.com/maps/documentation/javascript/coordinates
 */
class MercatorProjection {
    companion object {
        const val MERCATOR_RANGE = 256
        private val ORIGIN_WORLD_POINT = WorldPoint(MERCATOR_RANGE / 2.0, MERCATOR_RANGE / 2.0)
        const val PIXELS_PER_LON_DEGREE = MERCATOR_RANGE / 360.0
        const val PIXELS_PER_LON_RADIAN = MERCATOR_RANGE / (2 * PI)

        fun latLngToWorldPoint(latLng: LatLng): WorldPoint {
            val x = ORIGIN_WORLD_POINT.x + latLng.lng * PIXELS_PER_LON_DEGREE

            val siny = sin(degreesToRadians(latLng.lat))
            // NOTE(appleton): Truncating to 0.9999 effectively limits latitude to
            // 89.189.  This is about a third of a tile past the edge of the world tile.
            val boundedSiny = bound(siny, -0.9999, 0.9999)

            val y = ORIGIN_WORLD_POINT.y + 0.5 * ln((1 + boundedSiny) / (1 - boundedSiny)) * -PIXELS_PER_LON_RADIAN

            return WorldPoint(x, y)
        }

        fun worldPointToLatLng(worldPoint: WorldPoint): LatLng {
            val lng = (worldPoint.x - ORIGIN_WORLD_POINT.x) / PIXELS_PER_LON_DEGREE
            val latRadians = (worldPoint.y - ORIGIN_WORLD_POINT.y) / -PIXELS_PER_LON_RADIAN
            var lat = radiansToDegrees(2 * atan(exp(latRadians)) - PI / 2)

            return LatLng(lat, lng)
        }

        fun worldPointToTilePosition(worldPoint: WorldPoint): TilePosition {
            return TilePosition(
                    (worldPoint.x / TILE_WORLD_WIDTH).toInt(),
                    (worldPoint.y / TILE_WORLD_WIDTH).toInt()
            )
        }

        fun tilePositionToWorldPoint(tilePosition: TilePosition): WorldPoint {
            return WorldPoint(
                    tilePosition.x * TILE_WORLD_WIDTH,
                    tilePosition.y * TILE_WORLD_WIDTH
            )
        }

        fun latLngToMapPoint(map: MapTile, latLng: LatLng): MapPoint {
            return worldPointToMapPoint(map, latLngToWorldPoint(latLng))
        }

        fun mapPointToLatLng(map: MapTile, mapPoint: MapPoint): LatLng {
            return worldPointToLatLng(mapPointToWorldPoint(map, mapPoint))
        }

        fun mapPointToWorldPoint(map: MapTile, mapPoint: MapPoint): WorldPoint {
            val scale = 2.0.pow(map.zoom.toDouble())
            val topLeftWorldPoint = tilePositionToWorldPoint(map.position)
            return WorldPoint(
                    topLeftWorldPoint.x + (mapPoint.x) / scale,
                    topLeftWorldPoint.y + (mapPoint.y) / scale
            )
        }

        fun worldPointToMapPoint(map: MapTile, worldPoint: WorldPoint): MapPoint {
            val scale = 2.0.pow(map.zoom.toDouble())
            val topLeftWorldPoint = tilePositionToWorldPoint(map.position)

            return MapPoint(
                    ((worldPoint.x - topLeftWorldPoint.x) * scale).toInt(),
                    ((worldPoint.y - topLeftWorldPoint.y) * scale).toInt()
            )
        }

        private fun degreesToRadians(degrees: Double): Double {
            return degrees * (PI / 180)
        }

        private fun radiansToDegrees(radians: Double): Double {
            return radians / (PI / 180)
        }

        private fun bound(n: Double, lower: Double, upper: Double): Double {
            if (n < lower) return lower
            if (n > upper) return upper
            return n
        }
    }
}
