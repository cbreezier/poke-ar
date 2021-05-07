package io.lhuang.pokear.pokemon

import io.lhuang.pokear.map.MercatorProjection
import io.lhuang.pokear.map.WorldPoint
import io.lhuang.pokear.pokedex.POKEDEX
import io.lhuang.pokear.util.NamespacedResultSet
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.Instant

@Component
class PokemonSpawnRowMapper : RowMapper<PokemonSpawn> {
    override fun mapRow(resultSet: ResultSet, rowNum: Int): PokemonSpawn? {
        val rs = NamespacedResultSet(resultSet)

        val dexId = rs.getLong("pokemon.pokedex_id")
        val spawnId = rs.getLong("spawns.id")
        val pokemonId = rs.getLong("pokemon.id")
        val nickname = rs.getStringOrNull("pokemon.nickname")
        val hp = rs.getInt("pokemon.hp")
        val exp = rs.getInt("pokemon.exp")
        val bondExp = rs.getInt("pokemon.bond_exp")
        val worldPoint = WorldPoint(
                rs.getDouble("spawns.world_x"),
                rs.getDouble("spawns.world_y")
        )
        val latLng = MercatorProjection.worldPointToLatLng(worldPoint)
        val startTime = Instant.ofEpochSecond(rs.getLong("spawns.start_timestamp"))
        val endTime = Instant.ofEpochSecond(rs.getLong("spawns.end_timestamp"))

        return PokemonSpawn(
                spawnId,
                Pokemon(
                        pokemonId,
                        POKEDEX[dexId.toInt()] ?: error("No pokedex entry for $dexId"),
                        nickname,
                        hp,
                        exp,
                        bondExp,
                        null // TODO do this properly
                ),
                latLng.lat,
                latLng.lng,
                startTime,
                endTime
        )
    }
}
