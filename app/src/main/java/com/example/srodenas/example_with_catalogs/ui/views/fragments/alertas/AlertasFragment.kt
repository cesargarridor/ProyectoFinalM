package com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.srodenas.example_with_catalogs.R
import com.example.srodenas.example_with_catalogs.databinding.FragmentAlertasBinding
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert
import com.example.srodenas.example_with_catalogs.ui.viewmodel.alertas.AlertaViewModel
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs.AddAlert
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.dialogs.EditAlert
import com.example.srodenas.example_with_catalogs.ui.views.fragments.alertas.adapter.AdapterAlerts
import java.time.LocalDateTime

class AlertasFragment : Fragment() {
    // Binding para acceder a los elementos de la vista
    private var _binding: FragmentAlertasBinding? = null
    private val binding get() = _binding!!

    // ViewModel para gestionar la lógica de alertas
    private val viewModelAlerts: AlertaViewModel by viewModels()

    // Adaptador para el RecyclerView
    private lateinit var adapterAlerts: AdapterAlerts

    // Handler para manejar tareas repetitivas
    private val handler = Handler()
    private val updateInterval = 60000L // Intervalo de actualización en milisegundos (1 minuto)

    // Runnable para actualizar el fondo del RecyclerView periódicamente
    @RequiresApi(Build.VERSION_CODES.O)
    private val updateBackgroundRunnable = object : Runnable {
        override fun run() {
            adapterAlerts.notifyDataSetChanged() // actualizar la lista para aplicar los cambios en el fondo
            handler.postDelayed(this, updateInterval)
        }
    }

    // Inflar la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertasBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // Indicar que este fragmento tiene su propio menú de opciones
        return binding.root
    }

    // Inicializar componentes una vez la vista ha sido creada
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initEvent()
        handler.post(updateBackgroundRunnable) // Iniciar la tarea repetitiva para actualizar el fondo
    }

    // Inflar el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Manejar la selección de opciones en el menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                requireActivity().finish() // Cerrar la actividad al seleccionar la opción de cerrar sesión
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Inicializar el RecyclerView y su adaptador
    private fun initRecyclerView() {
        adapterAlerts = AdapterAlerts(
            ArrayList(),
            onDelete = {
                deleteAlert(it)
            },
            onDetails = {
                // Manejar la visualización de detalles de la alerta (sin implementación actual)
            },
            onEdit = { alert, position ->
                editAlert(alert, position)
            }
        )
        binding.myRecyclerViewAlerts.layoutManager = LinearLayoutManager(requireContext())
        binding.myRecyclerViewAlerts.adapter = adapterAlerts
    }

    // Inicializar eventos y observadores
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initEvent() {
        binding.btnAdd.setOnClickListener {
            val dialog = AddAlert { alert, selectedDateTime ->
                okOnAddAlert(alert, selectedDateTime) // selectedDateTime es ahora de tipo LocalDateTime?
            }
            dialog.show(requireActivity().supportFragmentManager, "Agregar Alerta")
        }

        viewModelAlerts.alerts.observe(viewLifecycleOwner, { alerts ->
            adapterAlerts.listAlerts.clear()
            adapterAlerts.listAlerts.addAll(alerts)
            adapterAlerts.notifyDataSetChanged()
        })
    }

    // Manejar la adición de una nueva alerta
    @RequiresApi(Build.VERSION_CODES.O)
    private fun okOnAddAlert(alert: Alert, selectedDateTime: LocalDateTime?) {
        val newAlert = Alert(
            userId = 0,
            textShort = alert.textShort,
            message = alert.message,
            alertDate = selectedDateTime ?: LocalDateTime.now() // Provee un valor predeterminado en caso de que sea null
        )
        viewModelAlerts.addAlert(newAlert)
        Toast.makeText(requireContext(), "Alerta agregada", Toast.LENGTH_LONG).show()
    }

    // Manejar la edición de una alerta
    private fun editAlert(alert: Alert, position: Int) {
        val dialog = EditAlert(alert) { updatedAlert ->
            viewModelAlerts.updateAlert(position, updatedAlert)
            Toast.makeText(requireContext(), "Alerta actualizada correctamente", Toast.LENGTH_LONG).show()
        }
        dialog.show(requireActivity().supportFragmentManager, "Editar Alerta")
    }

    // Manejar la eliminación de una alerta
    private fun deleteAlert(position: Int) {
        viewModelAlerts.deleteAlert(position)
    }

    // Limpiar recursos cuando la vista es destruida
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateBackgroundRunnable) // Detener la tarea repetitiva
        _binding = null // Limpiar el binding
    }
}
