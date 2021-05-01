package io.lhuang.pokear

import io.lhuang.pokear.MapBoundsTest.EpsilonEquals.Companion.epsilonEquals
import io.lhuang.pokear.map.*
import io.lhuang.pokear.map.MercatorProjection.Companion.latLngToWorldPoint
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.awt.image.BufferedImage
import kotlin.math.abs

class MapBoundsTest {
    @Test
    fun testBounds() {
        val mockImage = mock(BufferedImage::class.java)

        val map = MapTile(
                TilePosition(0, 0),
                16,
                mockImage,
                emptyMap()
        )
        val topLeft = latLngToWorldPoint(MercatorProjection.mapPointToLatLng(map, MapPoint(0, 0)))
        val botRight = latLngToWorldPoint(MercatorProjection.mapPointToLatLng(map, MapPoint(TILE_PIXEL_WIDTH, TILE_PIXEL_WIDTH)))

        val width = botRight.x - topLeft.x
        val height = botRight.y - topLeft.y

        assertThat(width, epsilonEquals(0.0078125))
        assertThat(height, epsilonEquals(0.0078125))
    }

    class EpsilonEquals(private val expectedValue: Double): BaseMatcher<Double>() {

        override fun describeTo(description: Description?) {
            description?.appendValue(expectedValue)
            description?.appendText(" with epsilon of ")
            description?.appendValue(EPSILON)
        }

        override fun matches(actual: Any?): Boolean {
            if (actual is Double) {
                return abs(actual - expectedValue) < EPSILON
            } else if (actual is Int) {
                return abs(actual - expectedValue) < EPSILON
            } else {
                return false
            }
        }

        companion object {
            const val EPSILON = 0.00000000001

            fun epsilonEquals(expectedValue: Double): EpsilonEquals {
                return EpsilonEquals(expectedValue)
            }
        }
    }

}
