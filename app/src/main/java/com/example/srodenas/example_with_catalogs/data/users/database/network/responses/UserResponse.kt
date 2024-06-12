package com.example.srodenas.example_with_catalogs.data.users.database.network.responses

import com.example.srodenas.example_with_catalogs.domain.users.models.User

data class UserResponse(
    val result: String,
    val message: String,
    val usuarios: List<User>
)
