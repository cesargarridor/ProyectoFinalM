package com.example.srodenas.example_with_catalogs.ui.views.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment

import com.cursoaristi.myapplication.models.Registro
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.domain.users.OnUserInteractionDialogListener
import java.io.ByteArrayOutputStream
import java.io.IOException

/*
* Fragmento que se encarga de conectar el layout del registro con la funcionalidad.
* Esta clase se encarga de inflar el layout uniedolo a esta lógica, inflar la vista,
* crear un gialogo emergente(AletrDialog), recoger la información ontroducida en los campos,
* y comprobar si ya existe el usuario creado con los datos introducidos. Si existe devuelve el "error"
* si no, lo crea y lo añade a Realm.
 */
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
