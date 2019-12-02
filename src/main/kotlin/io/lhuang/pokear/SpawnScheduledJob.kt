package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SpawnScheduledJob(
        val spawnService: SpawnService
) {
    @Scheduled(fixedRate = 1000 * 60 * 20) // 20 minutes
    fun spawnPokemon() {
        spawnService.spawnPokemon(LatLng(-33.860327135599825, 151.20133193998765), 20)
    }
}
