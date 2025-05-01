package com.example.campusstylist.backend.domain.service



import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.infrastructure.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    fun signup(username: String, password: String, role: String): User {
        val user = User(
            username = username,
            password = password, // In production, hash the password
            role = enumValueOf(role.uppercase())
        )
        return userRepository.create(user)
    }

    fun signin(username: String, password: String): User? {
        val user = userRepository.findByUsername(username)
        return if (user != null && user.password == password) user else null
    }
}