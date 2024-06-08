package com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {


    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val TOKEN_KEY = "token"
        private const val EMAIL_KEY = "email"
        private const val NAME_KEY = "name"
    }

    fun saveUserData(token: String, email: String, name: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.putString(EMAIL_KEY, email)
        editor.putString(NAME_KEY, name)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY, null)
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL_KEY, null)
    }

    fun getName(): String? {
        return prefs.getString(NAME_KEY, null)
    }
    fun isUserLoggedIn(): Boolean {
        return getToken() != null && getEmail() != null && getName() != null
    }

    fun clearUserData() {
        val editor = prefs.edit()
        editor.remove(TOKEN_KEY)
        editor.remove(EMAIL_KEY)
        editor.remove(NAME_KEY)
        editor.apply()
    }
}
