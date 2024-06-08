package com.cursoaristi.myapplication.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}