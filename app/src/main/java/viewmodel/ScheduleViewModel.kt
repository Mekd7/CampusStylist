package com.example.campusstylistcomposed.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Date

class ScheduleViewModel : ViewModel() {
    // State for selected date
    private val _selectedDate = mutableStateOf<Date?>(null)
    val selectedDate: Date? get() = _selectedDate.value

    // Event handlers
    fun onDateSelected(date: Date) {
        _selectedDate.value = date
    }

    fun onAddBooking() {
        // Handle add booking action (e.g., navigate to booking screen)
    }

    fun onEditSchedule() {
        // Handle edit schedule action (e.g., open schedule editor)
    }
}