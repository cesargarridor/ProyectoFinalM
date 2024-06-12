package com.example.srodenas.example_with_catalogs.ui.viewmodel.perfil

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil.SharedPreferencesManager

class PerfilViewModel(application: Application) : AndroidViewModel(application) {
    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?> get() = _userData

    private val sharedPreferencesManager = SharedPreferencesManager(application)

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val email = sharedPreferencesManager.getEmail()
        val name = sharedPreferencesManager.getName()

        Log.d("PerfilViewModel", "Cargando datos del usuario")
        Log.d("PerfilViewModel", "Email: $email, Nombre: $name")

        if (email != null && name != null) {
            val user = User().apply {
                this.email = email
                this.nombre = name
            }
            _userData.value = user
            Log.d("PerfilViewModel", "Datos del usuario cargados en LiveData")
        } else {
            _userData.value = null
            Log.d("PerfilViewModel", "Datos del usuario no encontrados")
        }
    }
}
