package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.adapter

import java.time.LocalDateTime
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.srodenas.example_with_catalogs.databinding.ItemAlertBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert
import java.time.format.DateTimeFormatter

class ViewHAlert(
    view: View,
    val onDelete: (Int) -> Unit,
    val onDetails: (Int) -> Unit,
    val onEdit: (Alert, Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding: ItemAlertBinding = ItemAlertBinding.bind(view)

    @RequiresApi(Build.VERSION_CODES.O)
    fun renderize(alert: Alert, position: Int) {
        with(binding) {
            txtNameAlert.text = alert.textShort
            dateAlert.text = alert.alertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            txtDescriptionShort.text = alert.textShort
            txtDescription.text = alert.message

            // Cambiar el color de fondo si la alerta ha pasado
            if (alert.alertDate.isBefore(LocalDateTime.now())) {
                root.setCardBackgroundColor(Color.RED)
            } else {
                root.setCardBackgroundColor(Color.WHITE)
            }

            btnDeleteAlert.setOnClickListener {
                onDelete(position)
            }

            btnDescriptionAlert.setOnClickListener {
                onDetails(position)
            }

            btnEditAlert.setOnClickListener {
                onEdit(alert, position)
            }
        }
    }
}
