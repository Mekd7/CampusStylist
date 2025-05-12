package com.example.campusstylistcomposed.domain.model

data class User(
    val id: Long,
    val role: String, // e.g., "CLIENT" or "HAIRDRESSER"
    val email: String,
    val username: String,
    val bio: String,
    val profilePicture: String?,
    val hasCreatedProfile: Boolean
)