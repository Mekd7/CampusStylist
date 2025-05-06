package com.example.campusstylist.backend.infrastructure.repository

import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.infrastructure.table.Users
import org.jetbrains.exposed.sql.*
//import org.jetbrains.exposed.sql.op.Op
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun create(user: User): User = transaction {
        val id = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[role] = user.role
            it[profilePicture] = user.profilePicture
            it[bio] = user.bio
            it[name] = user.name
            it[hasCreatedProfile] = user.hasCreatedProfile
            it[Users.email] = user.email // Ensure email is set during creation
        } get Users.id
        user.copy(id = id)
    }

    fun findByEmail(email: String): User? = transaction {
        Users.select { Op.build { Users.email eq email } }
            .map {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    password = it[Users.password],
                    role = it[Users.role],
                    profilePicture = it[Users.profilePicture],
                    bio = it[Users.bio],
                    name = it[Users.name],
                    hasCreatedProfile = it[Users.hasCreatedProfile],
                    email = it[Users.email] // Include email in the mapping
                )
            }
            .singleOrNull()
    }

    fun update(user: User): Boolean = transaction {
        user.id?.let {
            Users.update({ Op.build { Users.id eq it } }) {
                it[profilePicture] = user.profilePicture
                it[bio] = user.bio
                it[name] = user.name
                it[hasCreatedProfile] = true
                it[Users.email] = user.email // Update email if provided
            } > 0
        } ?: false
    }

    fun delete(id: Long): Boolean = transaction {
        Users.deleteWhere { Op.build { Users.id eq id } } > 0
    }
}