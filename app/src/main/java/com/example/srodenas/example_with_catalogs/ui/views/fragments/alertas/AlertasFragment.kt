package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.FragmentAlertasBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert
import com.example.srodenas.example_with_catalogs.ui.viewmodel.alertas.AlertaViewModel
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs.AddAlert
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs.EditAlert
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alerts.adapter.AdapterAlerts

class AlertasFragment : Fragment() {
    private var _binding: FragmentAlertasBinding? = null
    private val binding get() = _binding!!
    private val viewModelAlerts: AlertaViewModel by viewModels()

    private lateinit var adapterAlerts: AdapterAlerts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertasBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initEvent()
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

    private fun initRecyclerView() {
        adapterAlerts = AdapterAlerts(
            ArrayList(),
            onDelete = {
                deleteAlert(it)
            },
            onDetails = {
            },
            onEdit = { alert, position ->
                editAlert(alert, position)
            }
        )
        binding.myRecyclerViewAlerts.layoutManager = LinearLayoutManager(requireContext())
        binding.myRecyclerViewAlerts.adapter = adapterAlerts
    }

    private fun initEvent() {
        binding.btnAdd.setOnClickListener {
            val dialog = AddAlert { alert ->
                okOnAddAlert(alert)
            }
            dialog.show(requireActivity().supportFragmentManager, "Agregar Alerta")
        }

        viewModelAlerts.alerts.observe(viewLifecycleOwner, { alerts ->
            adapterAlerts.listAlerts.clear()
            adapterAlerts.listAlerts.addAll(alerts)
            adapterAlerts.notifyDataSetChanged()
        })
    }

    private fun okOnAddAlert(alert: Alert) {
        val newAlert = Alert(
            userId = 0,
            textShort = alert.textShort,
            message = alert.message,
            alertDate = alert.alertDate
        )
        viewModelAlerts.addAlert(newAlert)
        Toast.makeText(requireContext(), "Alerta agregada correctamente", Toast.LENGTH_LONG).show()
    }

    private fun editAlert(alert: Alert, position: Int) {
        val dialog = EditAlert(alert) { updatedAlert ->
            viewModelAlerts.updateAlert(position, updatedAlert)
            Toast.makeText(requireContext(), "Alerta actualizada correctamente", Toast.LENGTH_LONG).show()
        }
        dialog.show(requireActivity().supportFragmentManager, "Editar Alerta")
    }

    private fun deleteAlert(position: Int) {
        viewModelAlerts.deleteAlert(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
