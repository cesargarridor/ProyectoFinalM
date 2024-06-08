package com.example.srodenas.example_with_catalogs.ui.views.fragments.alerts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.srodenas.example_with_catalogs.databinding.ItemAlertBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert

class AdapterAlerts(
    var listAlerts: MutableList<Alert>,
    val onDelete: (Int) -> Unit,
    val onDetails: (Int) -> Unit,
    val onEdit: (Alert, Int) -> Unit
) : RecyclerView.Adapter<ViewHAlert>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAlert {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAlertBinding.inflate(layoutInflater, parent, false)
        return ViewHAlert(binding.root, onDelete, onDetails, onEdit)
    }

    override fun getItemCount(): Int = listAlerts.size

    override fun onBindViewHolder(holder: ViewHAlert, position: Int) {
        holder.renderize(listAlerts[position], position)
    }
}
