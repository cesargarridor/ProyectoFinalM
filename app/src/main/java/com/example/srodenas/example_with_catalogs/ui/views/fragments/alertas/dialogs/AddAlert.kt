package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.AddAlertDialogBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddAlert(
    val onNewAlertDialog: (Alert, LocalDateTime?) -> Unit // Cambiar a LocalDateTime?
) : DialogFragment() {

    private lateinit var binding: AddAlertDialogBinding
    private var selectedDateTime: LocalDateTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            val viewDialogNewAlert = inflater.inflate(R.layout.add_alert_dialog, null)
            binding = AddAlertDialogBinding.bind(viewDialogNewAlert)

            binding.txtViewFecha.setOnClickListener {
                DateTimePickerDialog(requireContext()) { dateTime ->
                    selectedDateTime = dateTime
                    binding.txtViewFecha.text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                }
            }

            builder.setView(viewDialogNewAlert)
                .setPositiveButton("Registrar Alerta") { dialog, _ ->
                    val newAlert = recoverDataLayout(viewDialogNewAlert)
                    if (newAlert.textShort.isNullOrEmpty() || newAlert.message.isNullOrEmpty() || newAlert.alertDate == null) {
                        Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                        getDialog()?.cancel()
                    } else {
                        onNewAlertDialog(newAlert, selectedDateTime)
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    getDialog()?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun recoverDataLayout(view: View): Alert {
        return Alert(
            userId = 0,
            textShort = binding.txtViewName.text.toString(),
            message = binding.txtViewMessage.text.toString(),
            alertDate = selectedDateTime ?: LocalDateTime.now() // or handle null case appropriately
        )
    }
}
