package com.example.srodenas.example_with_catalogs.domain.users

import com.cursoaristi.myapplication.models.Registro

interface OnUserInteractionDialogListener {
    //Inserta un nuevo usuario
    fun insertarUsuario(registro: Registro?)
}