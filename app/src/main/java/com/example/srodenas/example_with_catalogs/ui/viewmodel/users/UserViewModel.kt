package com.example.srodenas.example_with_catalogs.ui.viewmodel.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srodenas.example_with_catalogs.data.users.database.network.UserInterface
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class UserViewModel : ViewModel() {
    private val _usersLiveData = MutableLiveData<Result<List<User>>>()
    val usersLiveData: LiveData<Result<List<User>>> = _usersLiveData

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/api-pueblos/endp/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val userAPI = retrofit.create(UserInterface::class.java)

    fun showUsers(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userAPI.getUsers(token)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("UserViewModel", "Respuesta completa: $userResponse")
                    userResponse?.let {
                        Log.d("UserViewModel", "Estado de la respuesta: ${it.result}")
                        Log.d("UserViewModel", "Usuarios en el mensaje: ${it.usuarios}")
                    }
                    val users = userResponse?.usuarios ?: emptyList()
                    _usersLiveData.postValue(Result.success(users))
                    Log.d("UserViewModel", "Usuarios obtenidos: $users")
                } else {
                    _usersLiveData.postValue(Result.failure(Exception("Error fetching users")))
                    Log.e("UserViewModel", "Error en la respuesta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _usersLiveData.postValue(Result.failure(e))
                Log.e("UserViewModel", "Excepción al obtener usuarios", e)
            }
        }
    }
}
