package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.ui.screens.Appointment
import java.util.Date

class ManageScheduleViewModel : ViewModel() {
    private val _selectedDate = mutableStateOf<Date?>(null)
    val selectedDate: Date? get() = _selectedDate.value

    private val _appointments = mutableStateOf<List<Appointment>>(emptyList()) // Use mutableStateOf for Compose
    val appointments: State<List<Appointment>> = _appointments // Expose as State for Compose

    init {
        // Mock appointments for preview
        _appointments.value = listOf(
            Appointment("10:00 AM", "Client 1", "Braid", "APPROVED"),
            Appointment("2:00 PM", "Client 2", "Weave", "PENDING")
        )
    }

    fun onDateSelected(date: Date) {
        _selectedDate.value = date
        // In a real app, fetch appointments for the selected date from the backend
    }
}