package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.network.ApiService
import com.example.campusstylistcomposed.network.RetrofitClient
import com.example.campusstylistcomposed.network.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val apiService: ApiService = RetrofitClient.apiService) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isHairdresser = MutableStateFlow(false)
    val isHairdresser: StateFlow<Boolean> = _isHairdresser.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateUsername(value: String) { _username.value = value }
    fun updatePassword(value: String) { _password.value = value }
    fun setRole(isHairdresser: Boolean) { _isHairdresser.value = isHairdresser }

    fun signUp(onSuccess: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val role = if (_isHairdresser.value) "HAIRSTYLIST" else "STUDENT"
                val response = apiService.signUp(SignUpRequest(_username.value, _password.value, role))
                val isHairdresser = response.role.uppercase() == "HAIRSTYLIST"
                onSuccess(response.token, isHairdresser)
            } catch (e: Exception) {
                _errorMessage.value = "Signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}