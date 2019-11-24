package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpawnResource(
        private val mapService: MapService
) {

    @GetMapping("/terrain")
    fun getTerrain(
            @RequestParam(value = "lat") latitude: Double,
            @RequestParam(value = "lng") longitude: Double
    ): Location {
        val terrain = mapService.getTerrain(LatLng(latitude, longitude))

        return Location(latitude, longitude, terrain)
    }
}
