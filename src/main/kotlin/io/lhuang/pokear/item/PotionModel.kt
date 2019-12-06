package io.lhuang.pokear.item

data class PotionModel(
        override val price: Int,
        override val rarity: Double,
        override val type: ItemType,
        val healAmount: Int
) : ItemModel
