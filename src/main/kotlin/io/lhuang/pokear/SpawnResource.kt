package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpawnResource(
        private val mapService: MapService,
        private val habitatService: HabitatService,
        private val pokemonDao: PokemonDao,
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
        val spawns = habitats.flatMap { pokemonDao.getPokemonSpawns(it) }

        return SpawnPoint(latitude, longitude, terrain, habitats, spawns.sortedByDescending { it.spawnChance })
    }

    @GetMapping("/spawn")
    fun getSpawns(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): List<PokemonSpawn> {
        val center = LatLng(latitude, longitude)
        val width = 0.015
        val height = 0.015

        return spawnService.getSpawns(center, width, height)
    }

    @PostMapping("/spawn")
    fun spawnPokemon(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): ResponseEntity<Void> {
        val center = LatLng(latitude, longitude)
        val width = 0.015
        val height = 0.015

        spawnService.spawnPokemon(center, width, height, 15)

        return ResponseEntity.ok().build()
    }
}
