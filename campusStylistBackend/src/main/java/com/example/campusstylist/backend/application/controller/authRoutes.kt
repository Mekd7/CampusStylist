package com.example.campusstylist.backend.application.controller

import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.domain.service.UserService
import com.example.campusstylist.backend.infrastructure.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(userService: UserService) {
    route("/auth") {
        post("/register") {
            val user = call.receive<User>()
            try {
                val createdUser = userService.signup(user.email, user.password, user.role.toString())
                call.respond(HttpStatusCode.Created, createdUser)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.Conflict, "Email already exists")
            }
        }

        post("/login") {
            val user = call.receive<User>()
            val foundUser = userService.signin(user.email, user.password)
            if (foundUser != null) {
                val token = JwtConfig.generateToken(user.email)
                call.respond(mapOf("token" to token, "role" to foundUser.role.toString(), "hasCreatedProfile" to foundUser.hasCreatedProfile))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }
    }
}