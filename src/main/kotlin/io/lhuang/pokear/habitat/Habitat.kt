package io.lhuang.pokear.habitat

/**
 * A habitat is a general categorisation of a "type" of location. It is more complex than the
 * terrain which is literally a color-mapping of the pixel.
 *
 * A habitat is derived from the type of terrain as well as surrounding terrain and points of interest.
 *
 * Each pixel on a map can be thought of as "being" a type of terrain and "belonging" to one or more
 * types of habitat.
 */
enum class Habitat(val description: String) {
    GRASS("Grass"),
    GARDEN("Grass + garden nearby"),
    FOREST("Forest"),
    POND("Water - small amount"),
    LAKE("Water - medium amount"),
    OCEAN("Water - large amount"),
    SHORE("Ocean with land nearby"),
    SAND("Sand"),
    BEACH("Sand with water nearby"),
    MOUNTAIN("Rock with elevation"),
    ROAD("Road"),
    URBAN("Man-made"),
    CITY("Road or man-made with businesses nearby"),
    POWER("Urban with power plant nearby"),
    POLICE("Urban with police station nearby"),
    SCHOOL("Urban with school nearby"),
    ZOO("Urban with zoo nearby"),
    MUSEUM("Urban with museum nearby"),
    HOSPITAL("Urban with hospital nearby"),
    GRAVEYARD("Urban with graveyard nearby"),
    GYM("Urban with gym nearby"),
    FARM("Urban with farm nearby"),
}
