package io.lhuang.pokear.manager

import io.lhuang.pokear.dao.UserDao
import io.lhuang.pokear.model.UserModel
import io.lhuang.pokear.model.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserManager(
        @Autowired val userDao: UserDao
) {
    fun getUser(id: Long): UserModel? {
        return userDao.getUser(id)
    }

    fun getUserByOauthName(oAuthName: String): UserModel? {
        return userDao.getUserByOauthName(oAuthName)
    }

    fun registerUser(userRegistration: UserRegistration, oAuthName: String): UserModel? {
        return userDao.registerUser(userRegistration, oAuthName)
    }
}