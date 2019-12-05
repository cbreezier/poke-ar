package io.lhuang.pokear

import com.google.maps.model.LatLng
import io.lhuang.pokear.habitat.HabitatService
import io.lhuang.pokear.map.MapService
import io.lhuang.pokear.pokedex.PokedexDao
import io.lhuang.pokear.pokemon.PokemonSpawn
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
        val map = mapService.getMap(latLng)

        val terrain = habitatService.getTerrain(map)
        val habitats = habitatService.calculateHabitat(map)
        val spawns = habitats.flatMap { pokedexDao.getPokemonSpawns(it) }

        return SpawnPoint(latitude, longitude, terrain, habitats, spawns.sortedByDescending { it.spawnChance })
    }

    @GetMapping("/spawn")
    fun getSpawns(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): List<PokemonSpawn> {
        val center = LatLng(latitude, longitude)

        return spawnService.getSpawns(center)
    }

    @PostMapping("/spawn")
    fun spawnPokemon(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        val center = LatLng(latitude, longitude)

        spawnService.spawnPokemon(center, 15)

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
