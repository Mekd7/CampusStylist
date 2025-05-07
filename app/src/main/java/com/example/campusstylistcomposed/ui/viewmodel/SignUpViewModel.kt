package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isHairdresser = MutableStateFlow(false)
    val isHairdresser: StateFlow<Boolean> = _isHairdresser.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId.asStateFlow()

    fun updateEmail(value: String) { _email.value = value }
    fun updatePassword(value: String) { _password.value = value }
    fun setRole(isHairdresser: Boolean) { _isHairdresser.value = isHairdresser }

    fun signUp(onSuccess: (String, Boolean, Long?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Mock signup response
                val role = if (_isHairdresser.value) "HAIRSTYLIST" else "STUDENT"
                val userId = System.currentTimeMillis() // Mock user ID
                val hasCreatedProfile = false // Navigate to createProfile screen

                _userId.value = userId
                onSuccess(role, hasCreatedProfile, userId)
            } catch (e: Exception) {
                _errorMessage.value = "Mock signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
