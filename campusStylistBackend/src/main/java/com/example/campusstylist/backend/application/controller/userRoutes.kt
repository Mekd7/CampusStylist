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

fun Route.userRoutes(userService: UserService) {
    authenticate("auth-jwt") {
        get("/profile/{email}") {
            val principal = call.principal<JWTPrincipal>()
            val authenticatedEmail = principal?.payload?.getClaim("email")?.asString()
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Invalid token")

            val requestedEmail = call.parameters["email"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Email parameter is required")

            // Ensure the authenticated user can only access their own profile
            if (authenticatedEmail != requestedEmail) {
                return@get call.respond(HttpStatusCode.Forbidden, "You can only access your own profile")
            }

            val user = userService.findByEmail(requestedEmail)
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }

        put("/profile/{id}") {
            val principal = call.principal<JWTPrincipal>()
            val authenticatedEmail = principal?.payload?.getClaim("email")?.asString()
                ?: return@put call.respond(HttpStatusCode.Unauthorized, "Invalid token")

            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid user ID")

            val authenticatedUser = userService.findByEmail(authenticatedEmail)
                ?: return@put call.respond(HttpStatusCode.Unauthorized, "Authenticated user not found")

            // Ensure the authenticated user can only update their own profile
            if (authenticatedUser.id != id) {
                return@put call.respond(HttpStatusCode.Forbidden, "You can only update your own profile")
            }

            val user = call.receive<User>()
            val updated = userService.update(user.copy(id = id))
            if (updated) {
                call.respond(HttpStatusCode.OK, "Profile updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }

        delete("/profile/{id}") {
            val principal = call.principal<JWTPrincipal>()
            val authenticatedEmail = principal?.payload?.getClaim("email")?.asString()
                ?: return@delete call.respond(HttpStatusCode.Unauthorized, "Invalid token")

            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid user ID")

            val authenticatedUser = userService.findByEmail(authenticatedEmail)
                ?: return@delete call.respond(HttpStatusCode.Unauthorized, "Authenticated user not found")

            // Ensure the authenticated user can only delete their own profile
            if (authenticatedUser.id != id) {
                return@delete call.respond(HttpStatusCode.Forbidden, "You can only delete your own profile")
            }

            val success = userService.delete(id)
            if (success) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
    }
}