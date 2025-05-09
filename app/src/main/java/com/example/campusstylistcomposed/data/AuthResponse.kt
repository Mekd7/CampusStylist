package com.example.campusstylistcomposed.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val role: String,
    val userId: String, // Added userId
    val hasCreatedProfile: Boolean
)