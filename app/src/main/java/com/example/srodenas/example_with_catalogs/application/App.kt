package com.example.srodenas.example_with_catalogs.application

import android.app.Application
import com.example.srodenas.example_with_catalogs.domain.users.UserDataBaseSingleton
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UserDataBaseSingleton.init(this)
       // FirebaseApp.initializeApp(this)

    }
}

