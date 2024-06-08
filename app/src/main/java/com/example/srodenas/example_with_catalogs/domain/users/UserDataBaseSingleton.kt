package com.example.srodenas.example_with_catalogs.domain.users

import android.content.Context
import androidx.room.Room
import com.example.srodenas.example_with_catalogs.data.users.database.UserDataBase
import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao

object UserDataBaseSingleton {
    lateinit var database: UserDataBase
    lateinit var userDao: UserDao

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
