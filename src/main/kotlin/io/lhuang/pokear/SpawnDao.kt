package io.lhuang.pokear

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SpawnDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonSpawnRowMapper: PokemonSpawnRowMapper,
        private val worldPointRowMapper: WorldPointRowMapper
) {
    fun addSpawn(worldPoint: WorldPoint, pokemon: Pokemon, startTime: Instant, endTime: Instant) {
        jdbcTemplate.update("insert into spawns (world_x, world_y, pokemon_id, start_timestamp, end_timestamp) values (${worldPoint.x}, ${worldPoint.y}, ${pokemon.id}, ${startTime.epochSecond}, ${endTime.epochSecond})")
    }

    fun getSpawns(worldPoint: WorldPoint, width: Double, height: Double, now: Instant): List<PokemonSpawn> {
        val left = worldPoint.x - width / 2
        val right = worldPoint.x + width / 2
        val top = worldPoint.y - height / 2
        val bottom = worldPoint.y + height / 2
        return jdbcTemplate.query("select s.world_x, s.world_y, s.start_timestamp, s.end_timestamp, p.id, p.name from spawns s join pokemon p on s.pokemon_id = p.id where s.world_x > ${left} and s.world_x < ${right} and s.world_y > ${top} and s.world_y < ${bottom} and s.start_timestamp < ${now.epochSecond} and s.end_timestamp > ${now.epochSecond}", pokemonSpawnRowMapper)
    }

    fun cleanupSpawns(now: Instant) {
        jdbcTemplate.update("delete from spawns where end_timestamp < ${now.epochSecond}")
    }

    fun visitLocation(location: WorldPoint, now: Instant) {
        jdbcTemplate.update("insert into visited_locations (world_x, world_y, timestamp) values (${location.x}, ${location.y}, ${now.epochSecond}) on conflict (world_x, world_y) do update set timestamp = ${now.epochSecond}")
    }

    // TODO respect timestamp
    fun getVisitedLocations(): List<WorldPoint> {
        return jdbcTemplate.query("select world_x, world_y from visited_locations", worldPointRowMapper)
    }
}
