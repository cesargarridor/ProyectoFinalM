package com.example.srodenas.example_with_catalogs.ui.views.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

import com.example.srodenas.example_with_catalogs.domain.users.models.Registro
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.domain.users.OnUserInteractionDialogListener


class RegisterDialog : DialogFragment() {
    private var usuario: EditText? = null
    private var pass: EditText? = null
    private var email: EditText? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity: Activity? = activity
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_registro, null)
        cargarViews(view)
        return AlertDialog.Builder(activity)
            .setView(view)
            .setTitle("Registro de usuario")
            .setPositiveButton("Registrar") { dialog, which ->
                val registro = Registro()
                registro.nombre = usuario!!.text.toString()
                registro.password = pass!!.text.toString()
                registro.email = email!!.text.toString()
                val mListener =
                    context as OnUserInteractionDialogListener? //aquí hay que pasarle el fragmento al que se refiuere y no el contexto, como estaba antes
                mListener!!.insertarUsuario(registro)
            }
            .create()
    }

    private fun cargarViews(view: View) {
        usuario = view.findViewById(R.id.registro_usuario)
        pass = view.findViewById(R.id.registro_password)
        email = view.findViewById(R.id.registro_email)
    }
}
