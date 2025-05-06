package com.example.campusstylist.backend.domain.service

import com.example.campusstylist.backend.domain.model.Role
import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.infrastructure.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val userRepository: UserRepository) {

    fun signup(email: String, password: String, role: String): User {
        val parsedRole = try {
            Role.valueOf(role.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: $role")
        }

        val user = User(
            id = null,
            email = email,
            username = email.split("@")[0],
            password = password,
            role = parsedRole
        )

        return transaction {
            userRepository.findByEmail(email)?.let {
                throw IllegalArgumentException("Email already exists")
            }
            userRepository.create(user)
        }
    }

    fun signin(email: String, password: String): User? {
        val user = transaction {
            userRepository.findByEmail(email)
        } ?: return null

        if (user.password != password) { // In a real app, use password hashing
            return null
        }

        return user
    }

    fun findByEmail(email: String): User? {
        return transaction {
            userRepository.findByEmail(email)
        }
    }

    fun update(user: User): Boolean {
        return transaction {
            userRepository.update(user)
        }
    }

    fun delete(id: Long): Boolean {
        return transaction {
            userRepository.delete(id)
        }
    }
}