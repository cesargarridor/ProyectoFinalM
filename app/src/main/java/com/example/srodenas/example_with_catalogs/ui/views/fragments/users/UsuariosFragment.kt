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

class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var adapterUser: AdapterUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.myRecyclerViewUsers.layoutManager = LinearLayoutManager(context)
        adapterUser = AdapterUser(mutableListOf()) { position ->

        }
        binding.myRecyclerViewUsers.adapter = adapterUser

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


        userViewModel.showUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
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
