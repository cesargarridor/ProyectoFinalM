package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.srodenas.example_with_catalogs.databinding.EditAlertDialogBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert

class EditAlert(private val alert: Alert, private val onSave: (Alert) -> Unit) : DialogFragment() {

    private lateinit var binding: EditAlertDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        binding = EditAlertDialogBinding.inflate(LayoutInflater.from(context))

        binding.txtViewName.setText(alert.textShort)
        binding.txtViewMessage.setText(alert.message)
        binding.txtViewFecha.setText(alert.alertDate)

        builder.setView(binding.root)
            .setTitle("Editar Alerta")
            .setPositiveButton("Guardar") { dialog, id ->
                val updatedAlert = Alert(
                    userId = alert.userId,
                    textShort = binding.txtViewName.text.toString(),
                    message = binding.txtViewMessage.text.toString(),
                    alertDate = binding.txtViewFecha.text.toString()
                )
                onSave(updatedAlert)
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.dismiss()
            }

        return builder.create()
    }
}
