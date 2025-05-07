package com.example.campusstylistcomposed.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    val role: String
)