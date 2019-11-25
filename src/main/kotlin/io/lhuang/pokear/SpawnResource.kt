package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpawnResource(
        private val mapService: MapService,
        private val habitatService: HabitatService
) {

    @GetMapping("/terrain")
    fun getTerrain(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): Location {
        val latLng = LatLng(latitude, longitude)
        val map = mapService.getMap(latLng)

        val terrain = habitatService.getTerrain(map)
        val habitat = habitatService.calculateHabitat(latLng, map)

        return Location(latitude, longitude, terrain, habitat)
    }
}
