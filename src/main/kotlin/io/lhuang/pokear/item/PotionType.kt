package io.lhuang.pokear.item

enum class PotionType(
        val displayName: String,
        val healAmount: Int
) {
    POTION("Potion", 20),
    SUPER_POTION("Super potion", 50),
    HYPER_POTION("Hyper potion", 200),
    MAX_POTION("Max potion", Int.MAX_VALUE),
    REVIVE("Revive", Int.MAX_VALUE)
}
