package io.lhuang.pokear.item

data class PokeballModel(
        override val price: Int,
        override val rarity: Double,
        override val type: ItemType,
        val catchRate: Double,
        val maxLevel: Int,
        val bondExpBonus: Int
) : ItemModel
