package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateEmail(value: String) { _email.value = value }
    fun updatePassword(value: String) { _password.value = value }

    fun login(onSuccess: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Simulate API call or actual Retrofit call
                val response = com.example.campusstylistcomposed.network.RetrofitClient.authApiService.login(
                    com.example.campusstylistcomposed.data.AuthRequest(_email.value, _password.value, "")
                )
                val isHairdresser = response.role == "HAIRDRESSER"
                onSuccess(response.token, isHairdresser)
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}