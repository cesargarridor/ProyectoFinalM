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
import com.example.srodenas.example_with_catalogs.databinding.EditAlertDialogBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditAlert(
    private val alert: Alert,
    private val onEditAlertDialog: (Alert) -> Unit
) : DialogFragment() {

    private lateinit var binding: EditAlertDialogBinding
    private var selectedDateTime: LocalDateTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            val viewDialogEditAlert = inflater.inflate(R.layout.edit_alert_dialog, null)
            binding = EditAlertDialogBinding.bind(viewDialogEditAlert)

            binding.txtViewName.setText(alert.textShort)
            binding.txtViewMessage.setText(alert.message)
           // binding.txtViewFecha.text = alert.alertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            selectedDateTime = alert.alertDate

            binding.txtViewFecha.setOnClickListener {
                DateTimePickerDialog(requireContext()) { dateTime ->
                    selectedDateTime = dateTime
                    //binding.txtViewFecha.text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                }
            }

            builder.setView(viewDialogEditAlert)
                .setPositiveButton("Guardar cambios") { dialog, _ ->
                    val updatedAlert = recoverDataLayout(viewDialogEditAlert)
                    if (updatedAlert.textShort.isNullOrEmpty() || updatedAlert.message.isNullOrEmpty() || updatedAlert.alertDate == null) {
                        Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                        getDialog()?.cancel()
                    } else {
                        onEditAlertDialog(updatedAlert)
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    getDialog()?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun recoverDataLayout(view: View): Alert {
        return Alert(
            userId = alert.userId,
            textShort = binding.txtViewName.text.toString(),
            message = binding.txtViewMessage.text.toString(),
            alertDate = selectedDateTime ?: alert.alertDate // mantener la fecha original si no se selecciona una nueva
        )
    }
}
