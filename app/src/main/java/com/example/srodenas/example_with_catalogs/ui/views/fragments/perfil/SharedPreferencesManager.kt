package com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val USER_TOKEN = "user_token"
        private const val USER_EMAIL = "user_email"
        private const val USER_NAME = "user_name"
    }

    fun saveUserData(token: String, email: String, name: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    fun getUserToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun getUserEmail(): String? {
        return prefs.getString(USER_EMAIL, null)
    }

    fun getUserName(): String? {
        return prefs.getString(USER_NAME, null)
    }

    fun isUserLoggedIn(): Boolean {
        return getUserToken() != null && getUserEmail() != null && getUserName() != null
    }

    fun clearUserData() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove(USER_EMAIL)
        editor.remove(USER_NAME)
        editor.apply()
    }
}
