package com.example.campusstylistcomposed.ui.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class EditBookingUiState(
    val date: String = "2025-07-15", // Example default
    val time: String = "10:00 AM"
)

class EditBookingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EditBookingUiState())
    val uiState: StateFlow<EditBookingUiState> = _uiState

    fun onDateChanged(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChanged(time: String) {
        _uiState.value = _uiState.value.copy(time = time)
    }
}