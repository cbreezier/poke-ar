package io.lhuang.pokear

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

    ANY("Any"),
    NONE("None")
}
