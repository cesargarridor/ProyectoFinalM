package com.example.srodenas.example_with_catalogs.domain.users

import android.content.Context
import androidx.room.Room
import com.example.srodenas.example_with_catalogs.data.users.database.UserDataBase
import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao

// Singleton para manejar la base de datos de usuarios
object UserDataBaseSingleton {
    lateinit var database: UserDataBase
    lateinit var userDao: UserDao

    // Inicializar la base de datos
    fun init(context: Context) {
        synchronized(this) {
            if (!UserDataBaseSingleton::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "my_app_user"
                ).build()
                userDao = database.userDao()
            }
        }
    }
}
