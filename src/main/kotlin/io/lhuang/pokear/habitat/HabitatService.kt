package io.lhuang.pokear.habitat

import io.lhuang.pokear.map.*
import org.springframework.stereotype.Component
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList

/**
 * Calculate the terrain and habitat of points on a map.
 */
@Component
class HabitatService(
        val mapService: MapService
) {

    companion object {
        const val LAKE_THRESHOLD = 3000
        const val OCEAN_THRESHOLD = 20000

        const val URBAN_ROAD_THRESHOLD = 500

        const val SHORT_DISTANCE = 50
        val CARDINALS: Array<Vector> = arrayOf(
                Vector(1, 0),
                Vector(0, 1),
                Vector(-1, 0),
                Vector(0, -1)
        )
    }

    fun calculateHabitat(map: MapTile, mapPoint: MapPoint): List<Habitat> {
        val terrain = getTerrain(map, mapPoint)
        val habitats: MutableList<Habitat> = ArrayList()

        if (terrain == Terrain.WATER) {
            val waterBodyType = waterBodyType(map, mapPoint)
            if (waterBodyType == Habitat.OCEAN) {
                if (isCloseTo(map, mapPoint) { t -> t != Terrain.WATER }) {
                    habitats.add(Habitat.SHORE)
                } else {
                    habitats.add(Habitat.OCEAN)
                }
            } else {
                habitats.add(waterBodyType)
            }
        } else if (terrain == Terrain.GRASS) {
            if (isNearby(map, mapPoint, PointOfInterest.GARDEN, 100, 1)) {
                habitats.add(Habitat.GARDEN)
            } else {
                habitats.add(Habitat.GRASS)
            }
        } else if (terrain == Terrain.SAND) {
            if (isCloseTo(map, mapPoint) { t -> t == Terrain.WATER }) {
                habitats.add(Habitat.BEACH)
            } else {
                habitats.add(Habitat.SAND)
            }
        } else if (terrain == Terrain.FOREST) {
            habitats.add(Habitat.FOREST)
        } else if (terrain == Terrain.ROAD) {
            habitats.add(Habitat.ROAD)
        } else if (terrain == Terrain.EMPTY) {
            if (isNearby(map, mapPoint, PointOfInterest.RESTAURANT, 10, 3)) {
                habitats.add(Habitat.CITY)
            } else if (countTiles(map, mapPoint, SHORT_DISTANCE) { t -> t == Terrain.ROAD } > URBAN_ROAD_THRESHOLD) {
                habitats.add(Habitat.URBAN)
            }
        }

        return habitats
    }

    private fun countTiles(map: MapTile, startingPosition: MapPoint, searchRadius: Int, terrainType: (Terrain?) -> Boolean): Int {
        val seen: MutableSet<MapPoint> = HashSet()
        val queue: PriorityQueue<MapPoint> = PriorityQueue{
            p1: MapPoint, p2: MapPoint -> distance2(p1, startingPosition).compareTo(distance2(p2, startingPosition))
        }
        queue.offer(startingPosition)

        var terrainCount = 0

        while (queue.isNotEmpty()) {
            val position = queue.remove()

            if (seen.contains(position)) continue

            // Too far away already
            if (distance2(position, startingPosition) > searchRadius * searchRadius) break

            seen.add(position)

            val terrain = Terrain.fromColor(Color(map.imageData.getRGB(position.x, position.y)))
            if (terrainType.invoke(terrain)) {
                // Found what we're looking for!
                terrainCount++
            }

            for (dir in CARDINALS) {
                val nextPosition = MapPoint(position.x + dir.x, position.y + dir.y)
                if (nextPosition.x >= 0 && nextPosition.x < map.imageData.width && nextPosition.y >= 0 && nextPosition.y < map.imageData.height) {
                    queue.offer(nextPosition)
                }
            }
        }

        return terrainCount
    }

    private fun waterBodyType(map: MapTile, startingPosition: MapPoint): Habitat {
        val seen: MutableSet<MapPoint> = HashSet()
        val queue: Queue<MapPoint> = LinkedList()
        queue.offer(startingPosition)

        while (queue.isNotEmpty() && seen.size < OCEAN_THRESHOLD) {
            val position = queue.remove()

            if (seen.contains(position)) continue

            val terrain = Terrain.fromColor(Color(map.imageData.getRGB(position.x, position.y)))
            if (terrain != Terrain.WATER) continue

            seen.add(position)

            for (dir in CARDINALS) {
                val nextPosition = MapPoint(position.x + dir.x, position.y + dir.y)
                if (nextPosition.x >= 0 && nextPosition.x < map.imageData.width && nextPosition.y >= 0 && nextPosition.y < map.imageData.height) {
                    queue.offer(nextPosition)
                }
            }
        }

        if (seen.size >= OCEAN_THRESHOLD) {
            return Habitat.OCEAN
        } else if (seen.size > LAKE_THRESHOLD) {
            return Habitat.LAKE
        } else {
            return Habitat.POND
        }
    }

    private fun isCloseTo(map: MapTile, mapPoint: MapPoint, terrainType: (Terrain?) -> Boolean): Boolean {
        return countTiles(map, mapPoint, SHORT_DISTANCE, terrainType) > 0
    }

    private fun distance2(a: MapPoint, b: MapPoint): Int {
        val dx = a.x - b.x
        val dy = a.y - b.y
        return (dx * dx) + (dy * dy)
    }

    private fun isNearby(map: MapTile, mapPoint: MapPoint, pointOfInterest: PointOfInterest, distancePixels: Int, numNearbyThreshold: Int): Boolean {
        val numNearby = map.places[pointOfInterest]
                ?.map { MercatorProjection.latLngToMapPoint(map, it) }
                ?.map { distance2(it, mapPoint) }
                ?.filter { it <= distancePixels * distancePixels }
                ?.count()
                ?: 0
        return numNearby >= numNearbyThreshold
    }

    fun getTerrain(map: MapTile, mapPoint: MapPoint): Terrain? {
        val pixel = Color(map.imageData.getRGB(mapPoint.x, mapPoint.y))

        return Terrain.fromColor(pixel)
    }

    data class Vector(
            val x: Int,
            val y: Int
    )
}
