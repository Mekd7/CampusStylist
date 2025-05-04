package com.example.campusstylist.backend.domain.service

import com.example.campusstylist.backend.domain.model.Role
import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.infrastructure.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val userRepository: UserRepository) {

    fun signup(username: String, password: String, role: String): User {
        val parsedRole = try {
            Role.valueOf(role.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: $role")
        }

        val user = User(
            id = 0, // ID will be set by the repository
            username = username,
            password = password,
            role = parsedRole
        )

        return transaction {
            userRepository.findByUsername(username)?.let {
                throw IllegalArgumentException("Username already exists")
            }
            userRepository.create(user)
        }
    }

    fun signin(username: String, password: String): User? {
        val user = transaction {
            userRepository.findByUsername(username)
        } ?: return null

        if (user.password != password) { // In a real app, use password hashing
            return null
        }

        return user
    }
}