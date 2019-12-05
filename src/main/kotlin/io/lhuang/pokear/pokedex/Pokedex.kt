package io.lhuang.pokear.pokedex

data class Pokedex(
        val id: Long,
        val name: String,

        val baseHp: Int,
        val baseAtk: Int,
        val baseDef: Int,
        val baseSpAtk: Int,
        val baseSpDef: Int,
        val baseSpd: Int,

        val type1: Type,
        val type2: Type?
) {

    // TODO temporary standard stats until I put everything in the database
    constructor(id: Long, name: String) : this(
                id,
                name,

                100,
                100,
                100,
                100,
                100,
                100,

                Type.NORMAL,
                null
        )
}
