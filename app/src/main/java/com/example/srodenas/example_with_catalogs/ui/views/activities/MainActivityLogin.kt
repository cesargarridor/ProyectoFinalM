package com.example.srodenas.example_with_catalogs.ui.views.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.srodenas.example_with_catalogs.domain.users.models.Login
import com.example.srodenas.example_with_catalogs.domain.users.models.Registro
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.data.users.database.network.UserInterface
import com.example.srodenas.example_with_catalogs.domain.users.OnUserInteractionDialogListener
import com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil.SharedPreferencesManager
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityLogin : AppCompatActivity(), OnUserInteractionDialogListener {
    private var usuario: EditText? = null
    private var contraseña: EditText? = null
    private val MY_PERMISSIONS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        if (sharedPreferencesManager.isUserLoggedIn()) {
            startActivity(Intent(this@MainActivityLogin, MainActivity::class.java))
            finish()
            return
        }

        if (verificarPermiso()) {
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS)
        }
        inicializarCampos()
    }

    private fun inicializarCampos() {
        usuario = findViewById(R.id.editxt_usuario)
        contraseña = findViewById(R.id.editxt_contraseña)
    }

    fun iniciarLogin(view: View?) {
        GlobalScope.launch(Dispatchers.IO) {
            val login = Login()
            login.email = usuario?.text.toString()
            login.password = contraseña?.text.toString()
            val user = login(login)
            if (user != null) {
                runOnUiThread {
                    entrarConRegis(user)
                }
            } else {
                runOnUiThread {
                    showError()
                }
            }
        }
    }

    private suspend fun login(login: Login): User? {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/api-pueblos/endp/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val userAPI = retrofit.create(UserInterface::class.java)
        val call = userAPI.login(login)
        return try {
            val response = call?.execute()
            if (response?.isSuccessful == true) {
                val user = response.body()
                Log.d("MainActivityLogin", "Login exitoso: Usuario - $user")
                user
            } else {
                Log.e("MainActivityLogin", "Error en login: ${response?.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MainActivityLogin", "Excepción en login: ${e.message}")
            null
        }
    }

    // En MainActivityLogin
    private fun entrarConRegis(user: User?) {
        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)

        Log.d("MainActivityLogin", "Datos del usuario a guardar: Token - ${user?.token}, Email - ${user?.email}, Nombre - ${user?.nombre}")

        sharedPreferencesManager.saveUserData(user?.token ?: "", user?.email ?: "", user?.nombre ?: "")

        Log.d("MainActivityLogin", "Datos guardados: Email - ${user?.email}, Nombre - ${user?.nombre}")

        startActivity(Intent(this@MainActivityLogin, MainActivity::class.java))
    }

    fun iniciarRegistro(view: View?) {
        RegisterDialog().show(supportFragmentManager, "Registro Usuario")
    }

    private fun showError() {
        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
    }

    override fun insertarUsuario(registro: Registro?) {
        GlobalScope.launch(Dispatchers.IO) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2/api-pueblos/endp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val userAPI = retrofit.create(UserInterface::class.java)
            val call = userAPI.registro(registro)
            try {
                val response = call?.execute()
                if (response != null) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        runOnUiThread {
                            entrarConRegis(user)
                        }
                    } else {
                        Log.e("error", "Error insertando usuario")
                    }
                }
            } catch (e: Exception) {
                Log.e("error", e.message ?: "Error desconocido")
            }
        }
    }

    private fun verificarPermiso(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
    }
}
