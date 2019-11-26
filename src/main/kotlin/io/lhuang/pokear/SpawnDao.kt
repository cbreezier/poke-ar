package io.lhuang.pokear

import com.google.maps.model.LatLng
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class SpawnDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonSpawnRowMapper: PokemonSpawnRowMapper
) {
    fun addSpawn(location: LatLng, pokemon: Pokemon) {
        jdbcTemplate.update("insert into spawns (latitude, longitude, pokemon_id) values (${location.lat}, ${location.lng}, ${pokemon.id})")
    }

    fun getSpawns(location: LatLng, width: Double, height: Double): List<PokemonSpawn> {
        val left = location.lat - width / 2
        val right = location.lat + width / 2
        val top = location.lng - height / 2
        val bottom = location.lng + height / 2
        return jdbcTemplate.query("select s.latitude, s.longitude, p.id, p.name from spawns s join pokemon p on s.pokemon_id = p.id where s.latitude > ${left} and s.latitude < ${right} and s.longitude > ${top} and s.longitude < ${bottom}", pokemonSpawnRowMapper)
    }
}
