package com.example.srodenas.example_with_catalogs.domain.users.models

import android.util.Log
import com.example.srodenas.example_with_catalogs.data.users.database.dao.UserDao
import com.example.srodenas.example_with_catalogs.data.users.database.network.UserInterface
import com.example.srodenas.example_with_catalogs.data.users.database.network.responses.UserResponse
import com.example.srodenas.example_with_catalogs.domain.users.UserDataBaseSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class Repository private constructor(
    private val userDao: UserDao
) {
    companion object {
        val repo: Repository by lazy {
            Repository(UserDataBaseSingleton.userDao)
        }
    }

    private var loggedUser: User? = null

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/api-pueblos/endp/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val userInterface: UserInterface = retrofit.create(UserInterface::class.java)

    fun setLoggedUser(user: User) {
        loggedUser = user
    }

    fun getLoggedUser(): User? {
        return loggedUser
    }

  /*  suspend fun getUsersApi(token: String): List<User> {
        return withContext(Dispatchers.IO) {
            val call = userInterface.getUsers(token)
            val response = call?.execute()
            if (response?.isSuccessful == true) {
                response.body()?.let {
                    Log.d("Repository", "Respuesta de la API: ${it.message}")
                    return@withContext it.message
                }
            } else {
                Log.e("Repository", "Error en la respuesta de la API: ${response?.errorBody()?.string()}")
            }
            return@withContext emptyList()
        }
    }*/

}
