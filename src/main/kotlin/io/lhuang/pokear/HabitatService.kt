package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

@Component
class HabitatService(
        val mapService: MapService
) {

    companion object {
        const val LAKE_THRESHOLD = 3000
        const val OCEAN_THRESHOLD = 20000

        const val URBAN_ROAD_THRESHOLD = 500

        const val SHORT_DISTANCE = 50
        val CARDINALS: Array<Position> = arrayOf(
                Position(1, 0),
                Position(0, 1),
                Position(-1, 0),
                Position(0, -1)
        )
    }

    fun calculateHabitat(latLng: LatLng): Habitat {
        return calculateHabitat(latLng, mapService.getMap(latLng));
    }

    fun calculateHabitat(latLng: LatLng, map: BufferedImage): Habitat {
        val terrain = getTerrain(map)
        if (terrain == Terrain.WATER) {
            val waterBodyType = waterBodyType(map);
            if (waterBodyType == Habitat.OCEAN) {
                if (isCloseTo(map) { t -> t != Terrain.WATER }) {
                    return Habitat.SHORE
                } else {
                    return Habitat.OCEAN
                }
            } else {
                return waterBodyType
            }
        } else if (terrain == Terrain.GRASS) {
            if (isNearby(latLng, "garden")) {
                return Habitat.GARDEN
            } else {
                return Habitat.GRASS
            }
        } else if (terrain == Terrain.SAND) {
            if (isCloseTo(map) { t -> t == Terrain.WATER }) {
                return Habitat.BEACH
            } else {
                return Habitat.SAND
            }
        } else if (terrain == Terrain.FOREST) {
            return Habitat.FOREST
        } else if (terrain == Terrain.ROAD) {
            return Habitat.ROAD
        } else if (terrain == Terrain.EMPTY) {
            if (isNearby(latLng, "restaurant")) {
                return Habitat.CITY
            } else if (countTiles(map, SHORT_DISTANCE) { t -> t == Terrain.ROAD } > URBAN_ROAD_THRESHOLD) {
                return Habitat.URBAN
            } else {
                return Habitat.EMPTY
            }
        } else {
            return Habitat.EMPTY
        }
    }

    private fun countTiles(map: BufferedImage, searchRadius: Int, terrainType: (Terrain?) -> Boolean): Int {
        val startingPosition = Position(map.width / 2, map.height / 2)
        val seen: MutableSet<Position> = HashSet()
        val queue: PriorityQueue<Position> = PriorityQueue{
            p1: Position, p2: Position -> distance2(p1, startingPosition).compareTo(distance2(p2, startingPosition))
        }
        queue.offer(startingPosition)

        var terrainCount: Int = 0

        while (queue.isNotEmpty()) {
            val position = queue.remove()

            if (seen.contains(position)) continue

            // Too far away already
            if (distance2(position, startingPosition) > searchRadius * searchRadius) break

            seen.add(position)

            val terrain = Terrain.fromColor(Color(map.getRGB(position.x, position.y)))
            if (terrainType.invoke(terrain)) {
                // Found what we're looking for!
                terrainCount++
            }

            for (dir in CARDINALS) {
                val nextPosition = Position(position.x + dir.x, position.y + dir.y)
                if (nextPosition.x >= 0 && nextPosition.x < map.width && nextPosition.y >= 0 && nextPosition.y < map.height) {
                    queue.offer(nextPosition)
                }
            }
        }

        return terrainCount
    }

    private fun waterBodyType(map: BufferedImage): Habitat {
        val seen: MutableSet<Position> = HashSet()
        val queue: Queue<Position> = LinkedList()
        queue.offer(Position(map.width / 2, map.height / 2))

        while (queue.isNotEmpty() && seen.size < OCEAN_THRESHOLD) {
            val position = queue.remove()

            if (seen.contains(position)) continue

            val terrain = Terrain.fromColor(Color(map.getRGB(position.x, position.y)))
            if (terrain != Terrain.WATER) continue

            seen.add(position)

            for (dir in CARDINALS) {
                val nextPosition = Position(position.x + dir.x, position.y + dir.y)
                if (nextPosition.x >= 0 && nextPosition.x < map.width && nextPosition.y >= 0 && nextPosition.y < map.height) {
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

    private fun isCloseTo(map: BufferedImage, terrainType: (Terrain?) -> Boolean): Boolean {
        return countTiles(map, SHORT_DISTANCE, terrainType) > 0
    }

    private fun distance2(a: Position, b: Position): Int {
        val dx = a.x - b.x;
        val dy = a.y - b.y;
        return (dx * dx) + (dy * dy);
    }

    private fun isNearby(latLng: LatLng, searchTerm: String): Boolean {
        return false
    }

    fun getTerrain(map: BufferedImage): Terrain? {
        val pixel = Color(map.getRGB(map.width / 2, map.height / 2))

        return Terrain.fromColor(pixel)
    }

    data class Position(val x: Int, val y: Int)
}
