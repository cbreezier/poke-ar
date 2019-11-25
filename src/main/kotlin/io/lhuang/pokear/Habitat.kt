package io.lhuang.pokear

enum class Habitat(val description: String) {
    GRASS("Grass"),
    GARDEN("Grass + garden nearby"),
    FOREST("Forest"),
    URBAN("Road or man-made"),
    CITY("Road or man-made with businesses nearby"),
    POND("Water - small amount"),
    LAKE("Water - medium amount"),
    OCEAN("Water - large amount"),
    SHORE("Ocean with land nearby"),
    SAND("Sand"),
    BEACH("Sand with water nearby"),
    MOUNTAIN("Rock with elevation")
}
