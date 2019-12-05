package io.lhuang.pokear

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SpawnScheduledJob(
        val spawnService: SpawnService
) {
    @Scheduled(fixedRate = 1000 * 60 * SpawnService.SPAWN_FREQUENCY_MINUTES) // 1 minute
    fun spawnPokemon() {
        spawnService.spawnPokemon(20)
    }

    @Scheduled(fixedRate = 1000 * 60 * 5) // 5 minutes
    fun cleanupSpawns() {
        spawnService.cleanupSpawns()
    }
}
