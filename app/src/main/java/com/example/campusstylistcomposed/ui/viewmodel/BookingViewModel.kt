package com.example.campusstylistcomposed.ui.booking

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.ui.screens.Service
import com.example.campusstylistcomposed.ui.screens.Order
import java.util.Date

class BookingViewModel : ViewModel() {
    // State for BookingScreen
    private val _selectedDate = mutableStateOf<Date?>(null)
    val selectedDate: Date? get() = _selectedDate.value

    private val _selectedService = mutableStateOf<Service?>(null)
    val selectedService: Service? get() = _selectedService.value

    // Shared state for orders (used by both BookingScreen and OrderScreen)
    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> get() = _orders

    // Navigation signal
    private val _navigateToOrders = mutableStateOf(false)
    val navigateToOrders: Boolean get() = _navigateToOrders.value

    // Event handlers
    fun onDateSelected(date: Date) {
        _selectedDate.value = date
    }

    fun onServiceSelected(service: Service?) {
        _selectedService.value = service
    }

    fun confirmBooking() {
        _selectedService.value?.let { service ->
            _orders.add(Order(service = service.name))
            _selectedService.value = null
            _selectedDate.value = null
            _navigateToOrders.value = true // Signal navigation after state update
        }
    }

    fun clearSelection() {
        _selectedService.value = null
        _selectedDate.value = null
    }

    fun onNavigated() {
        _navigateToOrders.value = false // Reset navigation signal
    }
}