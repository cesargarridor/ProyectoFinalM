package com.example.srodenas.example_with_catalogs.data.users.database.network

import com.example.srodenas.example_with_catalogs.data.users.database.network.responses.UserResponse
import com.example.srodenas.example_with_catalogs.domain.users.models.Login
import com.example.srodenas.example_with_catalogs.domain.users.models.Registro
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserInterface {
    @Headers("Content-Type: application/json")
    @POST("auth")
    suspend fun login(@Body login: Login?): Response<User?>

    @Headers("Content-Type: application/json")
    @POST("registro")
    suspend fun registro(@Body registro: Registro?): Response<User?>

    @GET("user")
    suspend fun getUsers(@Header("api-key") token: String?): Response<UserResponse?>
}
