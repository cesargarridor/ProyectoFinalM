package com.example.srodenas.example_with_catalogs.ui.views.fragments.alerts.adapter

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.srodenas.example_with_catalogs.databinding.ItemAlertBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert
import java.time.LocalDate

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
            dateAlert.text = alert.alertDate
            txtDescriptionShort.text = alert.textShort
            txtDescription.text = alert.message

            // Verifica si la fecha de la alerta ha pasado
            val currentDate = LocalDate.now()
            val alertDate = LocalDate.parse(alert.alertDate)

            if (alertDate.isBefore(currentDate)) {
                root.setCardBackgroundColor(Color.parseColor("#FFCDD2")) // Color rojo claro para indicar que la alerta ha pasado
            } else {
                root.setCardBackgroundColor(Color.parseColor("#FFFFFF")) // Color blanco para las alertas que no han pasado
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
