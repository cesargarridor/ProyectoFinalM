package com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.FragmentPerfilBinding
import com.example.srodenas.example_with_catalogs.ui.viewmodel.perfil.PerfilViewModel

class PerfilFragment : Fragment() {

    private lateinit var viewModel: PerfilViewModel
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // Habilitar el menú de opciones en este fragmento
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        loadUserProfile() // Cargar los datos del perfil del usuario

    }

    private fun loadUserProfile() {
        // Obtener los datos del usuario de SharedPreferences y mostrarlos en la UI
        val email = sharedPreferencesManager.getEmail()
        val name = sharedPreferencesManager.getName()

        binding.textViewEmail.text = ("Email: " + email)
        binding.textViewName.text = ("Nombre: " + name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu) // Inflar el menú
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // Manejar la opción de cerrar sesión
                sharedPreferencesManager.clearUserData()
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
