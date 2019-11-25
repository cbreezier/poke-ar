package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

@Component
class HabitatService {

    companion object {
        const val OCEAN_THRESHOLD = 500
        const val SHORT_DISTANCE = 50
        val CARDINALS: Array<Position> = arrayOf(
                Position(1, 0),
                Position(0, 1),
                Position(-1, 0),
                Position(0, -1)
        )
    }

    fun calculateHabitat(latLng: LatLng, map: BufferedImage): Habitat? {
        val terrain = getTerrain(map)
        if (terrain == Terrain.WATER) {
            if (isOcean(map)) {
                if (isCloseTo(map) { t -> t != Terrain.WATER }) {
                    return Habitat.SHORE
                } else {
                    return Habitat.OCEAN
                }
            } else {
                return Habitat.POND
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
        } else if (terrain == Terrain.MANMADE || terrain == Terrain.ROAD) {
            if (isNearby(latLng, "restaurant")) {
                return Habitat.CITY
            } else {
                return Habitat.URBAN
            }
        } else {
            return null
        }
    }

    private fun isOcean(map: BufferedImage): Boolean {
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

        return seen.size >= OCEAN_THRESHOLD
    }

    private fun isCloseTo(map: BufferedImage, terrainType: (Terrain?) -> Boolean): Boolean {
        val startingPosition = Position(map.width / 2, map.height / 2)
        val seen: MutableSet<Position> = HashSet()
        val queue: PriorityQueue<Position> = PriorityQueue{
            p1: Position, p2: Position -> distance2(p1, startingPosition).compareTo(distance2(p2, startingPosition))
        }
        queue.offer(startingPosition)

        while (queue.isNotEmpty()) {
            val position = queue.remove()

            if (seen.contains(position)) continue

            // Too far away already
            if (distance2(position, startingPosition) > SHORT_DISTANCE * SHORT_DISTANCE) return false

            seen.add(position)

            val terrain = Terrain.fromColor(Color(map.getRGB(position.x, position.y)))
            if (terrainType.invoke(terrain)) {
                // Found what we're looking for!
                return true
            }

            for (dir in CARDINALS) {
                val nextPosition = Position(position.x + dir.x, position.y + dir.y)
                if (nextPosition.x >= 0 && nextPosition.x < map.width && nextPosition.y >= 0 && nextPosition.y < map.height) {
                    queue.offer(nextPosition)
                }
            }
        }

        return false
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
