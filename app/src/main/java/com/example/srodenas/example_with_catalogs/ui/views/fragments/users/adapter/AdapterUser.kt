package com.example.srodenas.example_with_catalogs.ui.views.fragments.users.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.srodenas.example_with_catalogs.domain.users.models.User
import com.example.srodenas.example_with_catalogs.databinding.ItemUserBinding

// Adapter para manejar la lista de usuarios en un RecyclerView
class AdapterUser(
    var listUsers: MutableList<User>,
    val onDetails: (Int) -> Unit
) : RecyclerView.Adapter<AdapterUser.ViewHUser>() {

    // Inflar la vista del item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHUser {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHUser(binding, onDetails)
    }

    // Obtener la cantidad de items en la lista
    override fun getItemCount(): Int = listUsers.size

    // Rellenar los datos del item en la posición especificada
    override fun onBindViewHolder(holder: ViewHUser, position: Int) {
        holder.renderize(listUsers[position], position)
    }

    // ViewHolder para cada item en la lista
    inner class ViewHUser(private val binding: ItemUserBinding, val onDetails: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        // Rellenar los datos del usuario en la vista
        fun renderize(user: User, position: Int) {
            binding.txtviewName.text = user.nombre
            binding.txtViewEmail.text = user.email

            // Configurar el botón de enviar email
            binding.btnEmail.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${user.email}")
                    putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                    putExtra(Intent.EXTRA_TEXT, "Body Here")
                }
                itemView.context.startActivity(emailIntent)
            }

            // Configurar el botón de llamada telefónica
            binding.btnCall.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${user.id}")
                }
                itemView.context.startActivity(callIntent)
            }
        }
    }
}
