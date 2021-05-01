package io.lhuang.pokear

import com.google.maps.model.LatLng
import io.lhuang.pokear.habitat.HabitatService
import io.lhuang.pokear.map.MapService
import io.lhuang.pokear.map.MercatorProjection.Companion.latLngToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToMapPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToTilePosition
import io.lhuang.pokear.model.PokemonSpawnModel
import io.lhuang.pokear.pokedex.PokedexDao
import io.lhuang.pokear.spawn.SpawnPoint
import io.lhuang.pokear.spawn.SpawnService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpawnResource(
        private val mapService: MapService,
        private val habitatService: HabitatService,
        private val pokedexDao: PokedexDao,
        private val spawnService: SpawnService
) {

    @GetMapping("/spawnInfo")
    fun getSpawnInfo(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): SpawnPoint {
        val latLng = LatLng(latitude, longitude)
        val worldPoint = latLngToWorldPoint(latLng)
        val tilePosition = worldPointToTilePosition(worldPoint)
        val map = mapService.getMap(tilePosition)
        val mapPoint = worldPointToMapPoint(map, worldPoint)

        val terrain = habitatService.getTerrain(map, mapPoint)
        val habitats = habitatService.calculateHabitat(map, mapPoint)
        val spawns = habitats.flatMap { pokedexDao.getPokemonSpawns(it) }

        return SpawnPoint(latitude, longitude, terrain, habitats, spawns.sortedByDescending { it.spawnChance })
    }

    @GetMapping("/spawn")
    fun getSpawns(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): List<PokemonSpawnModel> {
        val center = LatLng(latitude, longitude)

        return spawnService.getSpawns(center)
                .map { PokemonSpawnModel(it) }
    }

    @PostMapping("/spawn")
    fun spawnPokemon(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        val latLng = LatLng(latitude, longitude)
        val worldPoint = latLngToWorldPoint(latLng)
        val tilePosition = worldPointToTilePosition(worldPoint)

        spawnService.spawnPokemon(tilePosition, 15)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/visit")
    fun visitLocation(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        val location = LatLng(latitude, longitude)

        spawnService.visitLocation(location)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/visited")
    fun getVisitedLocations(): List<LatLng> {
        return spawnService.getVisitedLocations()
    }
}
