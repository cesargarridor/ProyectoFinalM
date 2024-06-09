package com.example.srodenas.example_with_catalogs.data.users.database

import com.example.srodenas.example_with_catalogs.domain.users.models.Login
import com.example.srodenas.example_with_catalogs.domain.users.models.Registro
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserInterface {
    @Headers("Content-Type: application/json")
    @POST("auth")
    fun login(@Body login: Login?): Call<User?>?

    @Headers("Content-Type: application/json")
    @POST("registro")
    fun registro(@Body registro: Registro?): Call<User?>?
    @GET("user")
    fun getUsers(): Call<List<User>>
}