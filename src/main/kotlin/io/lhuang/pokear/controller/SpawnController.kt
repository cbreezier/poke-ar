package io.lhuang.pokear.controller

import com.google.maps.model.LatLng
import io.lhuang.pokear.habitat.HabitatService
import io.lhuang.pokear.manager.UserManager
import io.lhuang.pokear.map.MapService
import io.lhuang.pokear.map.MercatorProjection.Companion.latLngToWorldPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToMapPoint
import io.lhuang.pokear.map.MercatorProjection.Companion.worldPointToTilePosition
import io.lhuang.pokear.model.PokemonSpawnModel
import io.lhuang.pokear.pokedex.PokedexDao
import io.lhuang.pokear.pokedex.PokedexManager
import io.lhuang.pokear.spawn.SpawnPoint
import io.lhuang.pokear.spawn.SpawnService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/spawns")
@Deprecated("These APIs are non-final and should be treated as deprecated and liable to change")
class SpawnController(
        private val userManager: UserManager,
        private val mapService: MapService,
        private val habitatService: HabitatService,
        private val pokedexManager: PokedexManager,
        private val spawnService: SpawnService
) {

    @GetMapping("/info")
    fun getSpawnInfo(
            @AuthenticationPrincipal principal: Principal,
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): SpawnPoint {
        getUserOrThrow(userManager, principal)

        val latLng = LatLng(latitude, longitude)
        val worldPoint = latLngToWorldPoint(latLng)
        val tilePosition = worldPointToTilePosition(worldPoint)
        val map = mapService.getMap(tilePosition)
        val mapPoint = worldPointToMapPoint(map, worldPoint)

        val terrain = habitatService.getTerrain(map, mapPoint)
        val habitats = habitatService.calculateHabitat(map, mapPoint)
        val spawns = habitats.flatMap { pokedexManager.getPokedexSpawnStats(it) }

        return SpawnPoint(latitude, longitude, terrain, habitats, spawns.sortedByDescending { it.spawnChance })
    }

    @GetMapping
    fun getSpawns(
            @AuthenticationPrincipal principal: Principal,
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): List<PokemonSpawnModel> {
        getUserOrThrow(userManager, principal)

        val center = LatLng(latitude, longitude)

        return spawnService.getSpawns(center)
                .map { PokemonSpawnModel(it) }
    }

    @PostMapping
    fun spawnPokemon(
            @AuthenticationPrincipal principal: Principal,
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        // TODO this is a debug/admin feature - should be disabled for normal users
        getUserOrThrow(userManager, principal)

        val latLng = LatLng(latitude, longitude)
        val worldPoint = latLngToWorldPoint(latLng)
        val tilePosition = worldPointToTilePosition(worldPoint)

        spawnService.spawnPokemon(tilePosition, 15)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/visit")
    fun visitLocation(
            @AuthenticationPrincipal principal: Principal,
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        // TODO record movement against the actual user
        getUserOrThrow(userManager, principal)

        val location = LatLng(latitude, longitude)

        spawnService.visitLocation(location)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/visited")
    fun getVisitedLocations(
            @AuthenticationPrincipal principal: Principal
    ): List<LatLng> {
        // TODO this is a debug/admin feature - should be disabled for normal users
        getUserOrThrow(userManager, principal)

        return spawnService.getVisitedLocations()
    }
}
