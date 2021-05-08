package io.lhuang.pokear.spawn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RegionManager(
        @Autowired val spawnDao: SpawnDao
) {
    fun getRegion(postcode: String, name: String, country: String): Region {
        val existingRegion = spawnDao.getRegion(postcode, country)
        return existingRegion ?: spawnDao.addRegion(Region.random(postcode, name, country))
    }
}