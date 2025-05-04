package com.example.campusstylistcomposed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.domain.AddToBookingsUseCase
import com.example.campusstylistcomposed.domain.DeclineRequestUseCase
import com.example.campusstylistcomposed.domain.GetRequestsUseCase
import com.example.campusstylistcomposed.domain.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI state to handle loading, success, and error
sealed class MyRequestsUiState {
    object Loading : MyRequestsUiState()
    data class Success(val requests: List<Request>) : MyRequestsUiState()
    data class Error(val message: String) : MyRequestsUiState()
}

// Navigation events
sealed class NavigationEvent {
    data class NavigateToAddBooking(val userName: String, val service: String) : NavigationEvent()
}

// UI events
sealed class MyRequestsEvent {
    data class AddToBookings(val request: Request) : MyRequestsEvent()
    data class Decline(val request: Request) : MyRequestsEvent()
}

@HiltViewModel
class MyRequestsViewModel @Inject constructor(
    private val getRequestsUseCase: GetRequestsUseCase,
    private val addToBookingsUseCase: AddToBookingsUseCase,
    private val declineRequestUseCase: DeclineRequestUseCase
) : ViewModel() {
    // UI state
    private val _uiState = MutableStateFlow<MyRequestsUiState>(MyRequestsUiState.Loading)
    val uiState: StateFlow<MyRequestsUiState> = _uiState.asStateFlow()

    // Navigation events channel
    private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvents: Flow<NavigationEvent> = _navigationEvents.receiveAsFlow()

    init {
        loadRequests()
    }

    fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = MyRequestsUiState.Loading
            try {
                val requests = getRequestsUseCase()
                _uiState.value = MyRequestsUiState.Success(requests)
            } catch (e: Exception) {
                _uiState.value = MyRequestsUiState.Error("Failed to load requests: ${e.message}")
            }
        }
    }

    fun onEvent(event: MyRequestsEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is MyRequestsEvent.AddToBookings -> {
                        val (userName, service) = addToBookingsUseCase(event.request)
                        _navigationEvents.send(
                            NavigationEvent.NavigateToAddBooking(
                                userName = userName,
                                service = service
                            )
                        )
                        // No reload here; navigation will handle the next step
                    }
                    is MyRequestsEvent.Decline -> {
                        declineRequestUseCase(event.request)
                        loadRequests()
                    }
                }
            } catch (e: Exception) {
                _uiState.value = MyRequestsUiState.Error("Action failed: ${e.message}")
            }
        }
    }
}