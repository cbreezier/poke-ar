package io.lhuang.pokear

import com.google.maps.model.LatLng
import io.lhuang.pokear.MercatorProjection.Companion.latLngToWorldPoint
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.awt.image.BufferedImage

class MapBoundsTest {
    @Test
    fun testBounds() {
        val mockImage = mock(BufferedImage::class.java)

        val map = MapData(
                LatLng(-33.0, 151.0),
                512,
                512,
                16,
                mockImage,
                emptyMap()
        )
        val topLeft = latLngToWorldPoint(MercatorProjection.mapPointToLatLng(map, MapPoint(0, 0)))
        val botRight = latLngToWorldPoint(MercatorProjection.mapPointToLatLng(map, MapPoint(512, 512)))

        val width = botRight.x - topLeft.x
        val height = botRight.y - topLeft.y

        assert(width == 0.0078125)
        assert(height == 0.0078125)
    }

}
