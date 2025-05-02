package com.example.campusstylistcomposed.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)