package com.example.campusstylist.backend.application.controller

import com.example.campusstylist.backend.domain.service.UserService
import com.example.campusstylist.backend.infrastructure.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable // Add this import
import org.slf4j.LoggerFactory

@Serializable // Add this annotation
data class AuthRequest(val username: String, val password: String, val role: String? = null)

@Serializable // Add this annotation
data class AuthResponse(val token: String)

fun Route.authRoutes(userService: UserService) {
    val logger = LoggerFactory.getLogger("AuthController")
    route("/auth") {
        post("/signup") {
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
                call.respond(AuthResponse(token))
            } catch (e: IllegalArgumentException) {
                logger.warn("Signup failed: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Signup failed")
            }
        }

        post("/signin") {
            val request = try {
                call.receive<AuthRequest>()
            } catch (e: BadRequestException) {
                logger.error("Failed to deserialize request body: ${e.message}", e)
                call.respond(HttpStatusCode.BadRequest, "Invalid request body: ${e.message}")
                return@post
            }
            logger.info("Received signin request: username=${request.username}")
            val user = userService.signin(request.username, request.password)
            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                return@post
            }
            val token = JwtConfig.generateToken(user.id, user.role.toString())
            call.respond(AuthResponse(token))
        }
    }
}