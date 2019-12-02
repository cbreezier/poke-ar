package io.lhuang.pokear

import com.google.maps.model.LatLng
import java.lang.Math.PI
import kotlin.math.*

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

        fun latLngToMapPoint(map: MapData, latLng: LatLng): MapPoint {
            return worldPointToMapPoint(map, latLngToWorldPoint(latLng))
        }

        fun mapPointToLatLng(map: MapData, mapPoint: MapPoint): LatLng {
            return worldPointToLatLng(mapPointToWorldPoint(map, mapPoint))
        }

        fun mapPointToWorldPoint(map: MapData, mapPoint: MapPoint): WorldPoint {
            val scale = 2.0.pow(map.zoom.toDouble())
            val centerWorldPoint = latLngToWorldPoint(map.center)
            return WorldPoint(
                    centerWorldPoint.x + (mapPoint.x - map.width / 2) / scale,
                    centerWorldPoint.y + (mapPoint.y - map.height / 2) / scale
            )
        }

        fun worldPointToMapPoint(map: MapData, worldPoint: WorldPoint): MapPoint {
            val scale = 2.0.pow(map.zoom.toDouble())
            val centerWorldPoint = latLngToWorldPoint(map.center)

            return MapPoint(
                    ((worldPoint.x - centerWorldPoint.x) * scale).toInt() + map.width / 2,
                    ((worldPoint.y - centerWorldPoint.y) * scale).toInt() + map.height / 2
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
