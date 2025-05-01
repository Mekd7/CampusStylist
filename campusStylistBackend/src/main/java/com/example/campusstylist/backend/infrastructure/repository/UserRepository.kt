package com.example.campusstylist.backend.infrastructure.repository


import com.example.campusstylist.backend.domain.model.Role
import com.example.campusstylist.backend.domain.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun create(user: User): User = transaction {
        val id = Users.insert {
            it[username] = user.username
            it[password] = user.password // In production, hash the password
            it[role] = user.role.toString()
        } get Users.id
        user.copy(id = id.value)
    }

    fun findByUsername(username: String): User? = transaction {
        Users.select { Users.username eq username }
            .map {
                User(
                    id = it[Users.id].value,
                    username = it[Users.username],
                    password = it[Users.password],
                    role = Role.valueOf(it[Users.role])
                )
            }
            .singleOrNull()
    }
}