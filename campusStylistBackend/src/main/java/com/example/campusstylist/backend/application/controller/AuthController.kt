package com.example.campusstylist.backend.application.controller

import com.example.campusstylist.backend.domain.service.UserService
import com.example.campusstylist.backend.infrastructure.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
data class AuthRequest(val username: String, val password: String, val role: String? = null)

@Serializable
data class AuthResponse(val token: String, val role: String) // Add role field

fun Route.authRoutes(userService: UserService) {
    val logger = LoggerFactory.getLogger("AuthController")

    // Remove /auth prefix to match frontend expectations
    route("/signup") {
        post {
            val request = try {
                call.receive<AuthRequest>()
            } catch (e: BadRequestException) {
                logger.error("Failed to deserialize request body: ${e.message}", e)
                call.respond(HttpStatusCode.BadRequest, "Invalid request body: ${e.message}")
                return@post
            }
            logger.info("Received signup request: username=${request.username}, role=${request.role}")
            if (request.role == null || !listOf("STUDENT", "HAIRDRESSER").contains(request.role.uppercase())) {
                logger.warn("Invalid role: ${request.role}")
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing role")
                return@post
            }
            try {
                val user = userService.signup(request.username, request.password, request.role)
                val token = JwtConfig.generateToken(user.id, user.role.toString())
                call.respond(AuthResponse(token, user.role.toString())) // Include role in response
            } catch (e: IllegalArgumentException) {
                logger.warn("Signup failed: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Signup failed")
            }
        }
    }

    route("/login") { // Change /auth/signin to /login
        post {
            val request = try {
                call.receive<AuthRequest>()
            } catch (e: BadRequestException) {
                logger.error("Failed to deserialize request body: ${e.message}", e)
                call.respond(HttpStatusCode.BadRequest, "Invalid request body: ${e.message}")
                return@post
            }
            logger.info("Received login request: username=${request.username}")
            val user = userService.signin(request.username, request.password)
            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                return@post
            }
            val token = JwtConfig.generateToken(user.id, user.role.toString())
            call.respond(AuthResponse(token, user.role.toString())) // Include role in response
        }
    }

    // Optional: Add /me endpoint for future use
    authenticate("auth-jwt") {
        get("/me") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()
            val role = principal?.payload?.getClaim("role")?.asString()
            if (username != null && role != null) {
                call.respond(HttpStatusCode.OK, mapOf("username" to username, "role" to role))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
            }
        }
    }
}