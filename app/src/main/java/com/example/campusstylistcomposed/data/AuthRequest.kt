package com.example.campusstylistcomposed.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String,
    val role: String
)