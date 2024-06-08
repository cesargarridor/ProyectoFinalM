package com.example.srodenas.example_with_catalogs.domain.alerts.models

import java.time.LocalDateTime

data class Alert(
    val userId: Int,
    val textShort: String,
    val message: String,
    val alertDate: LocalDateTime
)
