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
    private var imagenUsuario: ImageView? = null
    private var camara: ImageView? = null
    private var galeria: ImageView? = null
    private var bitMap: Bitmap? = null
    private var inicioCamara: ActivityResultLauncher<Intent>? = null
    private var inicioLecturaGaleria: ActivityResultLauncher<Intent>? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity: Activity? = activity
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_registro, null)
        cargarViews(view)
        crearInicioACtividadCamara()
        crearInicioActividadLeerImagenGaleria()
        camara!!.setOnClickListener { e: View? ->
            val intentCamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            inicioCamara!!.launch(intentCamara)
        }
        galeria!!.setOnClickListener { e: View? ->
            val intentGaleria = Intent(Intent.ACTION_GET_CONTENT)
            intentGaleria.type = "image/*"
            inicioLecturaGaleria!!.launch(intentGaleria)
        }
        return AlertDialog.Builder(activity)
            .setView(view)
            .setTitle("Registro de usuario")
            .setPositiveButton("Registrar") { dialog, which ->
                val registro = Registro()
                registro.nombre = usuario!!.text.toString()
                registro.password = pass!!.text.toString()
                registro.email = email!!.text.toString()
                var imageBase64 = ""
                if (bitMap != null) {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitMap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    imageBase64 =
                        "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
                registro.imagen = imageBase64
                registro.disponible = 1
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
        imagenUsuario = view.findViewById(R.id.imageView_user)
        camara = view.findViewById<View>(R.id.camara) as ImageView
        galeria = view.findViewById<View>(R.id.galeria) as ImageView
    }

    private fun crearInicioACtividadCamara() {
        inicioCamara = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult(),
            object : ActivityResultCallback<ActivityResult?> {
                override fun onActivityResult(result: ActivityResult?) {
                    if (result != null) {
                        bitMap = result.data!!.extras!!["data"] as Bitmap?
                    }
                    imagenUsuario!!.setImageBitmap(bitMap)
                }
            }
        )
    }

    private fun crearInicioActividadLeerImagenGaleria() {
        inicioLecturaGaleria = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagenUri = result.data!!.data
                imagenUsuario!!.setImageURI(imagenUri)
                try {
                    bitMap = MediaStore.Images.Media.getBitmap(
                        this.requireActivity().contentResolver,
                        imagenUri
                    )
                } catch (e: IOException) {
                }
            }
        }
    }
}