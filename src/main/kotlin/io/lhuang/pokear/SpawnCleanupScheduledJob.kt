package io.lhuang.pokear

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SpawnCleanupScheduledJob(
        val spawnService: SpawnService
) {
    @Scheduled(fixedRate = 1000 * 60 * 60) // 60 minutes
    fun cleanupSpawns() {
        spawnService.cleanupSpawns()
    }
}
