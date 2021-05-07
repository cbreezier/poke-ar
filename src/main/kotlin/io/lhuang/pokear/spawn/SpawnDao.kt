package io.lhuang.pokear.spawn

import io.lhuang.pokear.map.TilePosition
import io.lhuang.pokear.map.TilePositionRowMapper
import io.lhuang.pokear.map.WorldPoint
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.pokemon.PokemonSpawn
import io.lhuang.pokear.pokemon.PokemonSpawnRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SpawnDao(
        private val jdbcTemplate: JdbcTemplate,
        private val pokemonSpawnRowMapper: PokemonSpawnRowMapper,
        private val tilePositionRowMapper: TilePositionRowMapper
) {
    fun addSpawn(worldPoint: WorldPoint, pokemon: Pokemon, startTime: Instant, endTime: Instant) {
        jdbcTemplate.update("""
            with inserted_pokemon as (
                insert into pokemon (pokedex_id, hp, exp, bond_exp)
                values (${pokemon.pokedex.id}, ${pokemon.hp}, ${pokemon.exp}, ${pokemon.bondExp})
                returning id
            )
            insert into spawns (world_x, world_y, pokemon_id, start_timestamp, end_timestamp)
            values (${worldPoint.x}, ${worldPoint.y}, (select id from inserted_pokemon), ${startTime.epochSecond}, ${endTime.epochSecond})
        """.trimIndent())
    }

    fun getSpawns(worldPoint: WorldPoint, width: Double, height: Double, now: Instant): List<PokemonSpawn> {
        val left = worldPoint.x - width / 2
        val right = worldPoint.x + width / 2
        val top = worldPoint.y - height / 2
        val bottom = worldPoint.y + height / 2
        return jdbcTemplate.query("select " +
                "s.*, " +
                "p.* " +
                "from spawns s " +
                "join pokemon p on s.pokemon_id = p.id " +
                "where " +
                "s.world_x > ${left} " +
                "and s.world_x < ${right} " +
                "and s.world_y > ${top} " +
                "and s.world_y < ${bottom} " +
                "and s.start_timestamp < ${now.epochSecond} " +
                "and s.end_timestamp > ${now.epochSecond} ",
                pokemonSpawnRowMapper)
    }

    fun cleanupSpawns(now: Instant) {
        jdbcTemplate.update("delete from spawns where end_timestamp < ${now.epochSecond}")
    }

    fun visitLocation(location: TilePosition, now: Instant) {
        jdbcTemplate.update("insert into visited_locations (tile_x, tile_y, timestamp) values (${location.x}, ${location.y}, ${now.epochSecond}) on conflict (tile_x, tile_y) do update set timestamp = ${now.epochSecond}")
    }

    // TODO respect timestamp
    fun getVisitedLocations(): List<TilePosition> {
        return jdbcTemplate.query("select tile_x, tile_y from visited_locations", tilePositionRowMapper)
    }
}
