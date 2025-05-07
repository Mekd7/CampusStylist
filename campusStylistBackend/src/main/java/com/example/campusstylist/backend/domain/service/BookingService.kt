package com.example.campusstylist.backend.domain.service

import com.example.campusstylist.backend.domain.model.Booking
import com.example.campusstylist.backend.infrastructure.repository.BookingRepository
import org.jetbrains.exposed.sql.transactions.transaction

class BookingService(private val bookingRepository: BookingRepository) {

    fun create(booking: Booking): Booking {
        return transaction {
            try {
                bookingRepository.create(booking)
            } catch (e: Exception) {
                throw IllegalStateException("Failed to create booking: ${e.message}", e)
            }
        }
    }

    fun getByUserId(userId: Long, isHairdresser: Boolean): List<Booking> {
        return transaction {
            bookingRepository.findByUserId(userId, isHairdresser)
        }
    }

    fun update(booking: Booking): Boolean {
        return transaction {
            bookingRepository.update(booking)
        }
    }
}