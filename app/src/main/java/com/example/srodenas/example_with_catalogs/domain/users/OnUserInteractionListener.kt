package com.example.srodenas.example_with_catalogs.domain.users

import com.example.srodenas.example_with_catalogs.domain.users.models.Registro

// Interfaz para manejar la interacción del usuario en el diálogo
interface OnUserInteractionDialogListener {
    // Insertar un nuevo usuario
    fun insertarUsuario(registro: Registro?)
}
