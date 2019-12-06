package io.lhuang.pokear.item

data class Bag(
        val pokeballs: Map<PokeballType, Int>,
        val potions: Map<PotionType, Int>
) {
    constructor(items: List<Item>): this(
            items
                    .filter { PokeballType.values().map { it.name }.contains(it.type) }
                    .map { Pair(PokeballType.valueOf(it.type), it.count) }
                    .toMap(),
            items
                    .filter { PotionType.values().map { it.name }.contains(it.type) }
                    .map { Pair(PotionType.valueOf(it.type), it.count) }
                    .toMap()
    )

    fun toItems(): List<Item> {
        val pokeballList = pokeballs.entries
                .map { Item(it.key.name, it.value) }
        val potionList = pokeballs.entries
                .map { Item(it.key.name, it.value) }

        return pokeballList.plus(potionList)
    }
}
