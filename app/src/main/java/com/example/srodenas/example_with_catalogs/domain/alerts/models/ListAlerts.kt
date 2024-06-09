package com.example.srodenas.example_with_catalogs.domain.alerts.models

class ListAlerts private constructor()
{
    var alerts : MutableList<Alert>  = mutableListOf()
    companion object{
        val list : ListAlerts by lazy {
            ListAlerts()
        }
    }
}