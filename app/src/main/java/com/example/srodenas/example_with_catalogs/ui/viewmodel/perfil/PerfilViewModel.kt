package com.example.srodenas.example_with_catalogs.ui.viewmodel.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import com.example.srodenas.example_with_catalogs.domain.users.models.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilViewModel : ViewModel() {
    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?> get() = _userData

    private val repository = Repository.repo

    // Cargar los datos del usuario desde el repositorio
    //No se utiliza ya
    fun loadUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getLoggedUser()
            withContext(Dispatchers.Main) {
                _userData.value = user
            }
        }
    }
}
