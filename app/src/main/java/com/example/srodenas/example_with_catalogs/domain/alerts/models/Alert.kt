package com.example.srodenas.example_with_catalogs.domain.alerts.models

data class Alert(
    val id: Long = 0,
    val userId: Long,
    val textShort: String,
    val message: String,
    val alertDate: String
)