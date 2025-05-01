package com.example.campusstylist.backend

import com.example.campusstylist.backend.application.controller.authRoutes
import com.example.campusstylist.backend.domain.service.UserService
import com.example.campusstylist.backend.infrastructure.DatabaseConfig
import com.example.campusstylist.backend.infrastructure.repository.UserRepository
import com.example.campusstylist.backend.infrastructure.security.configureJwt
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        DatabaseConfig.init()
        install(ContentNegotiation) {
            json()
        }
        install(Authentication) {
            configureJwt()
        }
        install(Routing) {
            authRoutes(UserService(UserRepository()))
        }
    }.start(wait = true)
}