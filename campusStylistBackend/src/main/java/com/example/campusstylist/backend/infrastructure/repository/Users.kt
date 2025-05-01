package com.example.campusstylist.backend.infrastructure.repository

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable() {
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)
    val role = varchar("role", 50)
}