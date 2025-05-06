package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ScheduleUiState(
    val selectedMonth: String = "July 2025",
    val days: List<Int> = (1..31).toList()
)

class ManageScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState
}