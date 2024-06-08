package com.example.srodenas.example_with_catalogs.ui.views.fragments.alerts.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.srodenas.example_with_catalogs.databinding.ItemAlertBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert

class ViewHAlert(
    view: View,
    val onDelete: (Int) -> Unit,
    val onDetails: (Int) -> Unit,
    val onEdit: (Alert, Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding: ItemAlertBinding = ItemAlertBinding.bind(view)

    fun renderize(alert: Alert, position: Int) {
        with(binding) {
            txtNameAlert.text = alert.textShort
            dateAlert.text = alert.alertDate
            txtDescriptionShort.text = alert.textShort
            txtDescription.text = alert.message

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
