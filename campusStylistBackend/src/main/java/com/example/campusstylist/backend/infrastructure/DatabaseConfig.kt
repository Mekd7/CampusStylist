package com.example.campusstylist.backend.infrastructure

import com.example.campusstylist.backend.infrastructure.repository.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/campusstylist",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "Mekd7777" // Replace with the password you set during PostgreSQL installation
        )
        transaction {
            SchemaUtils.create(Users)
        }
    }
}