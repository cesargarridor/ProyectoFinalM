package com.example.srodenas.example_with_catalogs.ui.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.ActivityMainBinding
import com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si el usuario ya ha iniciado sesión
        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        if (!sharedPreferencesManager.isUserLoggedIn()) {
            // Si el usuario no ha iniciado sesión, redirigir al login
            startActivity(Intent(this, MainActivityLogin::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establecer la barra de herramientas (toolbar)
        setSupportActionBar(binding.toolbar)

        // Configuración de la navegación inferior
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        // Configurar la barra de herramientas con el controlador de navegación y la configuración de la barra de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.title = "Aplicación César"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // No necesitas inflar el menú aquí
        // menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()  // Manejar la opción de cerrar sesión
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun logout() {
        // Limpiar los datos del usuario y redirigir al login
        val sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.clearUserData()
        startActivity(Intent(this, MainActivityLogin::class.java))
        finish()
    }
}
