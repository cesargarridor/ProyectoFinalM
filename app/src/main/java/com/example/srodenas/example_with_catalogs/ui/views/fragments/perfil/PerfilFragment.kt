package com.cursoaristi.myapplication.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.FragmentPerfilBinding
import com.example.srodenas.example_with_catalogs.ui.viewmodel.perfil.PerfilViewModel
import com.example.srodenas.example_with_catalogs.ui.views.fragments.perfil.SharedPreferencesManager

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.textViewName2.text = user.nombre
                binding.textViewEmail2.text = user.email
            } else {
                loadUserProfile()
            }
        }

        viewModel.loadUserData()
    }

    private fun loadUserProfile() {
        val email = sharedPreferencesManager.getUserEmail()
        val name = sharedPreferencesManager.getUserName()

        binding.textViewEmail2.text = email
        binding.textViewName2.text = name
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
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
