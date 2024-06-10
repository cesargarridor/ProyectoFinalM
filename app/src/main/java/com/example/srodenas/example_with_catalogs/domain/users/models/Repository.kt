package com.example.srodenas.example_with_catalogs.domain.users.models

import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao
import com.example.srodenas.example_with_catalogs.data.users.database.entities.UserEntity
import com.example.srodenas.example_with_catalogs.domain.users.UserDataBaseSingleton

// Repositorio para manejar la lógica de datos de usuarios
class Repository private constructor(private val userDao : UserDao){
    companion object {
        val repo: Repository by lazy {
            Repository(UserDataBaseSingleton.userDao)
        }
    }

    private var loggedUser: User? = null

    // Establecer el usuario logueado
    // No se utiliza
    fun setLoggedUser(user: User) {
        loggedUser = user
    }

    // Obtener el usuario logueado
    fun getLoggedUser(): User? {
        return loggedUser
    }

    // Obtener todos los usuarios de la base de datos
    // No se utiliza
    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }
}
