package com.example.campusstylist.backend

import com.example.campusstylist.backend.application.controller.authRoutes
import com.example.campusstylist.backend.domain.service.UserService
import com.example.campusstylist.backend.infrastructure.DatabaseConfig
import com.example.campusstylist.backend.infrastructure.repository.UserRepository
import com.example.campusstylist.backend.infrastructure.repository.Users // Import Users from repository
import com.example.campusstylist.backend.infrastructure.security.configureJwt
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

// Data classes for API requests/responses
@Serializable
data class RequestDto(val id: Int, val userName: String, val service: String)

@Serializable
data class BookingDto(val userName: String, val service: String)

// Database table definitions
object Requests : Table() {
    val id = integer("id").autoIncrement()
    val userName = varchar("user_name", 255)
    val service = varchar("service", 255)
    override val primaryKey = PrimaryKey(id)
}

object Bookings : Table() {
    val id = integer("id").autoIncrement()
    val userName = varchar("user_name", 255)
    val service = varchar("service", 255)
    override val primaryKey = PrimaryKey(id)
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        // Initialize database via DatabaseConfig
        DatabaseConfig.init()

        // Create tables if they don't exist
        transaction {
            SchemaUtils.create(Requests, Bookings)

            // Seed requests table if empty
            if (Requests.selectAll().count() == 0L) {
                Requests.insert { it[userName] = "Amy"; it[service] = "braids" }
                Requests.insert { it[userName] = "Beza"; it[service] = "Lace Installation" }
                Requests.insert { it[userName] = "Biruck"; it[service] = "Lace Installation" }
                Requests.insert { it[userName] = "Mekd"; it[service] = "palestra" }
                Requests.insert { it[userName] = "Leul"; it[service] = "Hair wash" }
            }
        }

        install(ContentNegotiation) {
            json() // Ensure this works with kotlinx.serialization
        }
        install(Authentication) {
            configureJwt()
        }
        install(Routing) {
            authRoutes(UserService(UserRepository()))

            // GET /requests - Fetch all requests
            get("/requests") {
                val requests = transaction {
                    Requests.selectAll().map {
                        RequestDto(
                            id = it[Requests.id],
                            userName = it[Requests.userName],
                            service = it[Requests.service]
                        )
                    }
                }
                call.respond(requests)
            }

            // DELETE /requests/{id} - Delete a request
            delete("/requests/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@delete
                }

                val deletedRows = transaction {
                    Requests.deleteWhere { Requests.id eq id }
                }

                if (deletedRows > 0) {
                    call.respond(HttpStatusCode.OK, "Request deleted")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Request not found")
                }
            }

            // POST /bookings - Create a booking
            post("/bookings") {
                val booking = call.receive<BookingDto>()
                val newBookingId = transaction {
                    Bookings.insert {
                        it[userName] = booking.userName
                        it[service] = booking.service
                    } get Bookings.id
                }
                call.respond(HttpStatusCode.Created, "Booking created with ID $newBookingId")
            }

            // POST /requests/{id}/addToBooking - Add a request to bookings and delete the request
            post("/requests/{id}/addToBooking") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@post
                }

                val request = transaction {
                    Requests.select { Requests.id eq id }.singleOrNull()?.let {
                        RequestDto(
                            id = it[Requests.id],
                            userName = it[Requests.userName],
                            service = it[Requests.service]
                        )
                    }
                }

                if (request == null) {
                    call.respond(HttpStatusCode.NotFound, "Request not found")
                    return@post
                }

                transaction {
                    // Create booking
                    Bookings.insert {
                        it[userName] = request.userName
                        it[service] = request.service
                    }
                    // Delete request
                    Requests.deleteWhere { Requests.id eq id }
                }

                call.respond(HttpStatusCode.OK, mapOf(
                    "message" to "Booking created and request deleted",
                    "userName" to request.userName,
                    "service" to request.service
                ))
            }
        }
    }.start(wait = true)
}