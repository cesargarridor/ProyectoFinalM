package com.example.srodenas.example_with_catalogs.domain.users

import com.example.srodenas.example_with_catalogs.domain.users.models.Registro

interface OnUserInteractionDialogListener {
    //Inserta un nuevo usuario
    fun insertarUsuario(registro: Registro?)
}