package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.repository.AuthRepository
import com.example.campusstylistcomposed.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authRepository: AuthRepository
) : ViewModel() {
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

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    fun updateEmail(value: String) { _email.value = value }
    fun updatePassword(value: String) { _password.value = value }
    fun setRole(isHairdresser: Boolean) { _isHairdresser.value = isHairdresser }

    fun signUp(onSuccess: (String, Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val role = if (_isHairdresser.value) "HAIRDRESSER" else "CLIENT"
                val request = AuthRequest(
                    email = _email.value,
                    password = _password.value,
                    role = role
                )
                val response = apiService.signUp(request)
                _userId.value = response.userId
                authRepository.saveToken(
                    token = response.token,
                    role = response.role,
                    userId = response.userId
                )
                onSuccess(response.role, response.hasCreatedProfile, response.userId)
            } catch (e: Exception) {
                _errorMessage.value = "Signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}