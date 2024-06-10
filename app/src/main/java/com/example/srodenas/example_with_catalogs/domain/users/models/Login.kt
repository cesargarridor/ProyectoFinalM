package com.example.srodenas.example_with_catalogs.domain.users.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Modelo de datos para el login de usuario
class Login {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}
