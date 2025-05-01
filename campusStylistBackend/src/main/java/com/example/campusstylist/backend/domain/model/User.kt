

package com.example.campusstylist.backend.domain.model

data class User(
    val id: Long = 0,
    val username: String,
    val password: String, // In production, this should be hashed
    val role: Role
)

enum class Role {
    STUDENT, HAIRDRESSER
}