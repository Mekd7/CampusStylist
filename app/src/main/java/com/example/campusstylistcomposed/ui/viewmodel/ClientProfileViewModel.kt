package com.example.campusstylistcomposed.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClientProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // 1. Get stored token
                val token = getStoredToken()

                if (!token.isNullOrEmpty()) {
                    // 2. Call logout API
                    val response: Response<Unit> = apiService.logout("Bearer $token")

                    if (response.isSuccessful) {
                        // 3. Clear local session regardless of API response
                        clearSession()
                        _logoutSuccess.value = true
                        onSuccess() // Trigger navigation
                    } else {
                        handleLogoutError(response)
                    }
                } else {
                    // No token found - still clear local data
                    clearSession()
                    _logoutSuccess.value = true
                    onSuccess()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
                // Ensure cleanup even if network fails
                clearSession()
                _logoutSuccess.value = true
                onSuccess()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getStoredToken(): String? {
        val sharedPrefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getString("auth_token", null)
    }

    private fun clearSession() {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).edit().apply {
            remove("auth_token")
            remove("user_id")
            remove("user_role")
            remove("has_created_profile")
            apply()
        }
        // Clear any cached data if needed
    }

    private fun handleLogoutError(response: Response<*>) {
        _errorMessage.value = when (response.code()) {
            401 -> "Session expired"
            500 -> "Server error"
            else -> "Logout failed (${response.code()})"
        }
    }
}