package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.AddAlertDialogBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert


class AddAlert (
    val onNewAlertDialog: (Alert)-> Unit
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)

            val inflater = requireActivity().layoutInflater;


            val viewDialogNewAlert = inflater.inflate(R.layout.add_alert_dialog, null)
            builder.setView(viewDialogNewAlert)
                .setPositiveButton("Registrar Alerta",
                    DialogInterface.OnClickListener { dialog, id ->
                        val newAlert = recoverDataLayout(viewDialogNewAlert) as Alert
                        if (
                            newAlert.textShort.isNullOrEmpty() ||
                            newAlert.message.isNullOrEmpty() ||
                            newAlert.alertDate.isNullOrEmpty()

                        ){
                            Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                            getDialog()?.cancel()
                        }else{
                            onNewAlertDialog(newAlert)
                        }
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


    }

    private fun recoverDataLayout(view: View): Any {
        val binding = AddAlertDialogBinding.bind(view)
        /**    val fechaText = binding.txtViewFecha.text.toString()
        val fecha = DateConverter().toLocalDate(fechaText)*/
        return Alert(
            0,
            0,
            binding.txtViewName.text.toString(),
            binding.txtViewMessage.text.toString(),
            binding.txtViewFecha.text.toString(),
        )
    }
}

