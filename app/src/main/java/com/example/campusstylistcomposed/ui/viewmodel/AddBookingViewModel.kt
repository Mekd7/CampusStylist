package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AddBookingUiState(
    val date: String = "",
    val time: String = ""
)

class AddBookingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddBookingUiState())
    val uiState: StateFlow<AddBookingUiState> = _uiState

    fun onDateChanged(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChanged(time: String) {
        _uiState.value = _uiState.value.copy(time = time)
    }
}