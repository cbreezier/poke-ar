package io.lhuang.pokear.item

enum class PokeballType(
        val displayName: String,
        val price: Int,
        val rarity: Int,
        val maxLevel: Int,
        val bondExpBonus: Int
) {
    STARTER_BALL("Starter ball", 300, 10, 1, 1000),
    BOND_BALL("Bond ball", 1000, 1, 50, 500),
    POKE_BALL("Pokeball", 100, 20, 15, 0),
    GREAT_BALL("Great ball", 500, 5, 30, 0),
    ULTRA_BALL("Ultra ball", 800, 1, 50, 0),
    MASTER_BALL("Master ball", Int.MAX_VALUE, 0, 100, 0)
}
