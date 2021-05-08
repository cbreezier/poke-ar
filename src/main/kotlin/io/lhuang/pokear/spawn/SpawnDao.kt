package io.lhuang.pokear.spawn

import io.lhuang.pokear.map.TilePosition
import io.lhuang.pokear.map.TilePositionRowMapper
import io.lhuang.pokear.map.WorldPoint
import io.lhuang.pokear.pokedex.Type
import io.lhuang.pokear.pokemon.Pokemon
import io.lhuang.pokear.pokemon.PokemonSpawn
import io.lhuang.pokear.pokemon.PokemonSpawnRowMapper
import io.lhuang.pokear.util.NamespacedResultSet
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.Instant

@Component
class SpawnDao(
        private val jdbcTemplate: NamedParameterJdbcTemplate,
        private val pokemonSpawnRowMapper: PokemonSpawnRowMapper,
        private val tilePositionRowMapper: TilePositionRowMapper
) {
    fun addSpawn(worldPoint: WorldPoint, pokemon: Pokemon, startTime: Instant, endTime: Instant) {
        jdbcTemplate.update(
                """
                    with inserted_pokemon as (
                        insert into pokemon (pokedex_id, hp, exp, bond_exp)
                        values (:pokedex_id, :hp, :exp, :bondExp)
                        returning id
                    )
                    insert into spawns (world_x, world_y, pokemon_id, start_timestamp, end_timestamp)
                    values (:worldX, :worldY, (select id from inserted_pokemon), :startTime, :endTime)
                """.trimIndent(),
                mapOf(
                        "pokedex_id" to pokemon.pokedex.id,
                        "hp" to pokemon.hp,
                        "exp" to pokemon.exp,
                        "bondExp" to pokemon.bondExp,

                        "worldX" to worldPoint.x,
                        "worldY" to worldPoint.y,
                        "startTime" to startTime.epochSecond,
                        "endTime" to endTime.epochSecond
                )
        )
    }

    fun getSpawns(worldPoint: WorldPoint, width: Double, height: Double, now: Instant): List<PokemonSpawn> {
        val left = worldPoint.x - width / 2
        val right = worldPoint.x + width / 2
        val top = worldPoint.y - height / 2
        val bottom = worldPoint.y + height / 2
        return jdbcTemplate.query(
                "select " +
                "s.*, " +
                "p.* " +
                "from spawns s " +
                "join pokemon p on s.pokemon_id = p.id " +
                "where " +
                "s.world_x > :left " +
                "and s.world_x < :right " +
                "and s.world_y > :top " +
                "and s.world_y < :bottom " +
                "and s.start_timestamp < :now " +
                "and s.end_timestamp > :now ",
                mapOf(
                        "left" to left,
                        "right" to right,
                        "top" to top,
                        "bottom" to bottom,
                        "now" to now.epochSecond
                ),
                pokemonSpawnRowMapper
        )
    }

    fun cleanupSpawns(now: Instant) {
        jdbcTemplate.update("delete from spawns where end_timestamp < :now", mapOf("now" to now.epochSecond))
    }

    fun visitLocation(location: TilePosition, now: Instant) {
        jdbcTemplate.update(
                "insert into visited_locations (tile_x, tile_y, timestamp) " +
                        "values (:tileX, :tileY, :now) " +
                        "on conflict (tile_x, tile_y) do update set timestamp = :now",
                mapOf(
                        "tileX" to location.x,
                        "tileY" to location.y,
                        "now" to now.epochSecond
                )
        )
    }

    // TODO respect timestamp
    fun getVisitedLocations(): List<TilePosition> {
        return jdbcTemplate.query("select tile_x, tile_y from visited_locations", tilePositionRowMapper)
    }

    fun getRegion(locality: String, state: String, country: String): Region? {
        return try {
            jdbcTemplate.queryForObject(
                    "select * from regions where locality = :locality and state = :state and country = :country",
                    mapOf(
                            "locality" to locality,
                            "state" to state,
                            "country" to country
                    ),
                    regionRowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun addRegion(region: Region): Region {
        jdbcTemplate.update(
                "insert into regions(locality, state, country, min_level, max_level, generation, gym_type) " +
                        "values(:locality, :state, :country, :min_level, :max_level, :generation, :gym_type)",
                mapOf(
                        "locality" to region.locality,
                        "state" to region.state,
                        "country" to region.country,
                        "min_level" to region.minLevel,
                        "max_level" to region.maxLevel,
                        "generation" to region.generation,
                        "gym_type" to region.gymType.name
                )
        )

        return region
    }

    companion object {
        val regionRowMapper = RowMapper { rs: ResultSet, _: Int ->
            val resultSet = NamespacedResultSet(rs)
            Region(
                    resultSet.getString("regions.locality"),
                    resultSet.getString("regions.state"),
                    resultSet.getString("regions.country"),
                    resultSet.getInt("regions.min_level"),
                    resultSet.getInt("regions.max_level"),
                    resultSet.getInt("regions.generation"),
                    Type.valueOf(resultSet.getString("regions.gym_type"))
            )
        }
    }
}
