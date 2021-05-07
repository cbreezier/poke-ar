package io.lhuang.pokear.pokemon

import io.lhuang.pokear.map.MercatorProjection
import io.lhuang.pokear.map.WorldPoint
import io.lhuang.pokear.pokedex.POKEDEX
import io.lhuang.pokear.pokedex.Pokedex
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.Instant

@Component
class PokemonSpawnRowMapper : RowMapper<PokemonSpawn> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PokemonSpawn? {
        val dexId = rs.getLong("dex_id")
        val spawnId = rs.getLong("spawn_id")
        val pokemonId = rs.getLong("pokemon_id")
        val hp = rs.getInt("hp")
        val exp = rs.getInt("exp")
        val bondExp = rs.getInt("bond_exp")
        val worldPoint = WorldPoint(
                rs.getDouble("world_x"),
                rs.getDouble("world_y")
        )
        val latLng = MercatorProjection.worldPointToLatLng(worldPoint)
        val startTime = Instant.ofEpochSecond(rs.getLong("start_timestamp"))
        val endTime = Instant.ofEpochSecond(rs.getLong("end_timestamp"))

        return PokemonSpawn(
                spawnId,
                Pokemon(
                        pokemonId,
                        POKEDEX[dexId.toInt()] ?: error("No pokedex entry for $dexId"),
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
