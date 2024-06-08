package com.example.srodenas.example_with_catalogs.data.users.database

import com.cursoaristi.myapplication.models.Login
import com.cursoaristi.myapplication.models.Registro
import com.cursoaristi.myapplication.models.User
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
    @GET("users") // Asume que este es el endpoint para obtener los usuarios
    fun getUsers(): Call<List<User>>
}