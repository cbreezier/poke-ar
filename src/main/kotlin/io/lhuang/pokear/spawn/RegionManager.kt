package io.lhuang.pokear.spawn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RegionManager(
        @Autowired val spawnDao: SpawnDao
) {
    fun getRegion(locality: String, state: String, country: String): Region {
        val existingRegion = spawnDao.getRegion(locality, state, country)
        return existingRegion ?: spawnDao.addRegion(Region.random(locality, state, country))
    }
}