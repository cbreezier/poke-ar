package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SpawnDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonSpawnRowMapper: PokemonSpawnRowMapper
) {
    fun addSpawn(location: LatLng, pokemon: Pokemon, startTime: Instant, endTime: Instant) {
        jdbcTemplate.update("insert into spawns (latitude, longitude, pokemon_id, start_timestamp, end_timestamp) values (${location.lat}, ${location.lng}, ${pokemon.id}, ${startTime.epochSecond}, ${endTime.epochSecond})")
    }

    fun getSpawns(location: LatLng, width: Double, height: Double, now: Instant): List<PokemonSpawn> {
        val left = location.lat - width / 2
        val right = location.lat + width / 2
        val top = location.lng - height / 2
        val bottom = location.lng + height / 2
        return jdbcTemplate.query("select s.latitude, s.longitude, s.start_timestamp, s.end_timestamp, p.id, p.name from spawns s join pokemon p on s.pokemon_id = p.id where s.latitude > ${left} and s.latitude < ${right} and s.longitude > ${top} and s.longitude < ${bottom} and s.start_timestamp < ${now.epochSecond} and s.end_timestamp > ${now.epochSecond}", pokemonSpawnRowMapper)
    }

    fun cleanupSpawns(now: Instant) {
        jdbcTemplate.update("delete from spawns where end_timestamp < ${now.epochSecond}")
    }
}
