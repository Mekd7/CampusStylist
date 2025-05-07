package com.example.campusstylist.backend.application.controller

import com.example.campusstylist.backend.domain.model.User
import com.example.campusstylist.backend.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import java.sql.SQLException

fun Route.userRoutes(userService: UserService) {
    authenticate("auth-jwt") {
        get("/profile/{id}") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString() ?: return@get call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "Invalid token"))
                val id = call.parameters["id"]?.toLongOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid input", "message" to "Invalid user ID"))
                val user = userService.findByEmail(email) ?: return@get call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "User not found"))
                if (user.id != id) {
                    return@get call.respond(HttpStatusCode.Forbidden, mapOf("error" to "Forbidden", "message" to "You can only access your own profile"))
                }
                call.respond(user)
            } catch (e: SQLException) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Database error", "message" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Server error", "message" to e.message))
            }
        }

        put("/profile/{id}") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString() ?: return@put call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "Invalid token"))
                val id = call.parameters["id"]?.toLongOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid input", "message" to "Invalid user ID"))
                val user = userService.findByEmail(email) ?: return@put call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "User not found"))
                if (user.id != id) {
                    return@put call.respond(HttpStatusCode.Forbidden, mapOf("error" to "Forbidden", "message" to "You can only update your own profile"))
                }
                val updatedUser = call.receive<User>()
                if (updatedUser.email.isBlank()) {
                    return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid input", "message" to "Email is required"))
                }
                val updated = userService.update(updatedUser.copy(id = id))
                if (updated) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Profile updated"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Not found", "message" to "User not found"))
                }
            } catch (e: SerializationException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid JSON", "message" to e.message))
            } catch (e: SQLException) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Database error", "message" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Server error", "message" to e.message))
            }
        }

        delete("/profile/{id}") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString() ?: return@delete call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "Invalid token"))
                val id = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid input", "message" to "Invalid user ID"))
                val user = userService.findByEmail(email) ?: return@delete call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized", "message" to "User not found"))
                if (user.id != id) {
                    return@delete call.respond(HttpStatusCode.Forbidden, mapOf("error" to "Forbidden", "message" to "You can only delete your own profile"))
                }
                val success = userService.delete(id)
                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Not found", "message" to "User not found"))
                }
            } catch (e: SQLException) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Database error", "message" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Server error", "message" to e.message))
            }
        }
    }
}