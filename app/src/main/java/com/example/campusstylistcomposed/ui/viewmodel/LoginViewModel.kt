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
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun login(onSuccess: (String, Boolean, String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = AuthRequest(
                    email = _email.value,
                    password = _password.value,
                    role = "" // Not used for login
                )
                val response = apiService.login(request)
                authRepository.saveToken(response.token)
                val isHairdresser = response.role.uppercase() == "HAIRDRESSER"

                // Fetch userId from /user/me
                val userId = fetchUserId(response.token)
                onSuccess(response.token, isHairdresser, userId)
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchUserId(token: String): String {
        return try {
            val response = apiService.getUserId("Bearer $token")
            response.userId ?: token.hashCode().toString() // Fallback if endpoint fails
        } catch (e: Exception) {
            token.hashCode().toString() // Fallback to hash if API call fails
        }
    }
}
