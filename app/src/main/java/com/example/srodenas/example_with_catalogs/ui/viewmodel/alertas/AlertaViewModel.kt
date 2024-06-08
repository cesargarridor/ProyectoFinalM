package com.example.srodenas.example_with_catalogs.ui.viewmodel.alertas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.srodenas.example_with_catalogs.domain.alerts.models.Alert

class AlertaViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Alertas"
    }
    val text : LiveData<String> = _text
    val alerts = MutableLiveData<MutableList<Alert>>()

    fun addAlert(alert: Alert) {
        val currentList = alerts.value ?: mutableListOf()
        currentList.add(alert)
        alerts.value = currentList
    }

    fun updateAlert(index: Int, updatedAlert: Alert) {
        val currentList = alerts.value ?: return
        if (index >= 0 && index < currentList.size) {
            currentList[index] = updatedAlert
            alerts.value = currentList
        }
    }

    fun deleteAlert(index: Int) {
        val currentList = alerts.value ?: return
        if (index >= 0 && index < currentList.size) {
            currentList.removeAt(index)
            alerts.value = currentList
        }
    }
}