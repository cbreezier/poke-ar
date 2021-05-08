package io.lhuang.pokear.model

data class UserModel(
        val id: Long,
        val oAuthName: String,
        val username: String,
        val money: Int
)

data class UserRegistration(
        val username: String
)