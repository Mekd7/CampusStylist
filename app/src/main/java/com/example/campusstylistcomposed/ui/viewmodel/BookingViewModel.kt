package com.example.campusstylistcomposed.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.network.ApiService
import com.example.campusstylistcomposed.ui.screens.Service
import com.example.campusstylistcomposed.ui.screens.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import com.example.campusstylistcomposed.domain.model.Booking
import retrofit2.HttpException
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _selectedDate = mutableStateOf<Date?>(null)
    val selectedDate: Date? get() = _selectedDate.value

    private val _selectedService = mutableStateOf<Service?>(null)
    val selectedService: Service? get() = _selectedService.value

    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> get() = _orders

    private val _navigateToOrders = mutableStateOf(false)
    val navigateToOrders: Boolean get() = _navigateToOrders.value

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage // Expose as State

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading // Expose as State

    private val _hairstylistId = mutableStateOf<Long?>(null)
    val hairstylistId: Long? get() = _hairstylistId.value

    fun initialize(token: String, hairstylistId: Long?) {
        _hairstylistId.value = hairstylistId
        viewModelScope.launch {
            try {
                val userProfile = apiService.getUserProfile("Bearer $token")
                if (hairstylistId == null && userProfile.role == "HAIRDRESSER") {
                    _hairstylistId.value = userProfile.id.toLong()
                }
            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    401 -> "Invalid token. Please log in again."
                    404 -> "User not found."
                    else -> "Failed to load user data: ${e.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load user data: ${e.message}"
            }
        }
    }

    fun onDateSelected(date: Date) {
        _selectedDate.value = date
        checkAvailability(date)
    }

    fun onServiceSelected(service: Service?) {
        _selectedService.value = service
    }

    fun confirmBooking(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val userProfile = apiService.getUserProfile("Bearer $token")
                val clientId = userProfile.id.toLong()
                val booking = Booking(
                    clientId = clientId,
                    hairstylistId = hairstylistId ?: throw IllegalStateException("Hairstylist not selected"),
                    service = selectedService?.name ?: "",
                    price = selectedService?.price?.removeSuffix(" Birr")?.toDoubleOrNull() ?: 0.0,
                    date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(selectedDate ?: Date()),
                    status = "Pending",
                    username = null
                )
                val response = apiService.createBooking(booking)
                _orders.add(Order(service = response.service))
                _selectedService.value = null
                _selectedDate.value = null
                _navigateToOrders.value = true
            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    400 -> "Invalid booking details. Please check your selection."
                    401 -> "Authentication failed. Please log in again."
                    403 -> "You are not authorized to make this booking."
                    else -> "Failed to confirm booking: ${e.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to confirm booking: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun checkAvailability(date: Date) {
        viewModelScope.launch {
            try {
                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                val bookings = apiService.getBookingsByHairstylistDate(hairstylistId ?: return@launch, dateStr)
                if (bookings.isNotEmpty()) {
                    _errorMessage.value = "Hairdresser is fully booked on this date."
                    _selectedDate.value = null
                }
            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    400 -> "Invalid date or hairstylist ID."
                    401 -> "Authentication failed. Please log in again."
                    else -> "Failed to check availability: ${e.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to check availability: ${e.message}"
            }
        }
    }

    fun clearSelection() {
        _selectedService.value = null
        _selectedDate.value = null
        _errorMessage.value = null
    }

    fun onNavigated() {
        _navigateToOrders.value = false
    }

    fun setOrdersForPreview(orders: List<Order>) {
        _orders.clear()
        _orders.addAll(orders)
    }
}