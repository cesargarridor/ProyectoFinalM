package com.example.srodenas.example_with_catalogs.domain.users.models

import com.cursoaristi.myapplication.models.User
import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao
import com.example.srodenas.example_with_catalogs.data.users.database.entities.UserEntity
import com.example.srodenas.example_with_catalogs.domain.users.UserDataBaseSingleton


class Repository  private constructor(private val userDao : UserDao){
    companion object{
        val repo: Repository by lazy {
            Repository(UserDataBaseSingleton.userDao)
        }
    }
    val repo = this

    private var loggedUser: User? = null

    fun setLoggedUser(user: User) {
        loggedUser = user
    }

    fun getLoggedUser(): User? {
        return loggedUser
    }

    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }
}