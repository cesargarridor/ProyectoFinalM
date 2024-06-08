package com.example.srodenas.example_with_catalogs.ui.views.fragments.users.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cursoaristi.myapplication.models.User
import com.example.srodenas.example_with_catalogs.databinding.ItemUserBinding

class AdapterUser(
    var listUsers: MutableList<User>,
    val onDetails: (Int) -> Unit
) : RecyclerView.Adapter<AdapterUser.ViewHUser>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHUser {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHUser(binding, onDetails)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHUser, position: Int) {
        holder.renderize(listUsers[position], position)
    }

    inner class ViewHUser(private val binding: ItemUserBinding, val onDetails: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun renderize(user: User, position: Int) {
            binding.txtviewName.text = user.nombre
            binding.txtViewEmail.text = user.email

            binding.btnEmail.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${user.email}")
                    putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                    putExtra(Intent.EXTRA_TEXT, "Body Here")
                }
                itemView.context.startActivity(emailIntent)
            }
        }
    }
}
