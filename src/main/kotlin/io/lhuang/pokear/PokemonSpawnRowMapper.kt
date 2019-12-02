package io.lhuang.pokear

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.Instant

@Component
class PokemonSpawnRowMapper : RowMapper<PokemonSpawn> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PokemonSpawn? {
        val id = rs.getLong("id")
        val name = rs.getString("name")
        val worldPoint = WorldPoint(
                rs.getDouble("world_x"),
                rs.getDouble("world_y")
        )
        val latLng = MercatorProjection.worldPointToLatLng(worldPoint)
        val startTime = Instant.ofEpochSecond(rs.getLong("start_timestamp"))
        val endTime = Instant.ofEpochSecond(rs.getLong("end_timestamp"))

        return PokemonSpawn(
                Pokemon(id, name),
                latLng.lat,
                latLng.lng,
                startTime,
                endTime
        )
    }
}
