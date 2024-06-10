package com.example.srodenas.example_with_catalogs.ui.views.fragments.users

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.FragmentUsuariosBinding
import com.example.srodenas.example_with_catalogs.ui.viewmodel.users.UserViewModel
import com.example.srodenas.example_with_catalogs.ui.views.fragments.users.adapter.AdapterUser

// Fragmento que maneja la vista de la lista de usuarios
class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var adapterUser: AdapterUser

    // Inflar el diseño del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    // Configuración inicial del fragmento
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Configurar el RecyclerView con un LayoutManager y un Adapter
        binding.myRecyclerViewUsers.layoutManager = LinearLayoutManager(context)
        adapterUser = AdapterUser(mutableListOf()) { position ->

        }
        binding.myRecyclerViewUsers.adapter = adapterUser

        // Observar los cambios en la lista de usuarios y actualizar el Adapter
        userViewModel.usersLiveData.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = { users ->
                    Log.d("UsuariosFragment", "Usuarios recibidos: ${users.size}")
                    adapterUser.listUsers = users.toMutableList()
                    adapterUser.notifyDataSetChanged()
                },
                onFailure = { error ->
                    Log.e("UsuariosFragment", "Error al cargar usuarios", error)
                }
            )
        })

        // Solicitar la lista de usuarios al ViewModel
        userViewModel.showUsers()
    }

    // Inflar el menú de opciones en este fragmento
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Manejar las opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Limpiar la referencia al binding cuando se destruye la vista
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
