package com.example.campusstylist.backend.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long, // Changed to Long to match Users.id
    val username: String,
    val password: String,
    val role: Role
)

enum class Role {
    STUDENT, HAIRDRESSER
}