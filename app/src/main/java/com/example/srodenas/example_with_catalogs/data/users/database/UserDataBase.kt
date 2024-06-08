package com.example.srodenas.example_with_catalogs.data.users.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.srodenas.example_with_catalogs.data.users.database.converters.DateConverter
import com.example.srodenas.example_with_catalogs.data.users.database.dao.AlertDao
import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao
import com.example.srodenas.example_with_catalogs.data.users.database.entities.AlertEntity
import com.example.srodenas.example_with_catalogs.data.users.database.entities.UserEntity


@Database(
    entities = [UserEntity::class,
        AlertEntity::class],
        version = 1

)

@TypeConverters(DateConverter::class)
abstract class UserDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun alertsDao(): AlertDao



}
