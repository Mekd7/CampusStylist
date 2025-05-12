package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.data.repository.AuthRepository
import com.example.campusstylistcomposed.network.ApiService
import com.example.campusstylistcomposed.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClientProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isProfileDeleted = MutableStateFlow(false)
    val isProfileDeleted: StateFlow<Boolean> = _isProfileDeleted.asStateFlow()

    // Fetch the user profile data by userId
    fun fetchProfile(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val userId = authRepository.userId.value?.toLongOrNull()
                    ?: throw IllegalStateException("User ID not found")

                val response = apiService.getProfile(userId, "Bearer $token")

                // 🌐 Base URL of your backend for loading images
                val baseUrl = "http://10.0.2.2:8080" // For emulator accessing local server

                // 🛠️ Fix image path before updating user state
                val fixedUser = response.copy(
                    profilePicture = response.profilePicture?.let {
                        if (it.startsWith("http")) it else "$baseUrl/uploads/$it"
                    }
                )

                _user.value = fixedUser

            } catch (e: retrofit2.HttpException) {
                _errorMessage.value = when (e.code()) {
                    401 -> "Session expired. Please log in again."
                    403 -> "You can only access your own profile."
                    else -> "Failed to fetch profile: ${e.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message ?: "Unknown error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Logout the user and clear session
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val token = authRepository.token.value
                if (!token.isNullOrEmpty()) {
                    val response: Response<Unit> = apiService.logout("Bearer $token")
                    if (response.isSuccessful) {
                        authRepository.clearToken()
                        onSuccess()
                    } else {
                        handleLogoutError(response)
                    }
                } else {
                    authRepository.clearToken()
                    onSuccess()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
                authRepository.clearToken()
                onSuccess()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun handleLogoutError(response: Response<*>) {
        _errorMessage.value = when (response.code()) {
            401 -> "Session expired"
            500 -> "Server error"
            else -> "Logout failed (${response.code()})"
        }
        authRepository.clearToken()
    }

    // Delete the user profile
    fun deleteProfile(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {

                val userId = authRepository.userId.value?.toLongOrNull()
                    ?: throw IllegalStateException("User ID not found")

                val response = apiService.deleteUser(userId, "Bearer $token")

                if (response.isSuccessful) {
                    authRepository.clearToken()
                    _isProfileDeleted.value = true
                    onSuccess()
                } else {
                    _errorMessage.value = when (response.code()) {
                        401 -> "Session expired. Please log in again."
                        403 -> "You don't have permission to delete this account."
                        else -> "Failed to delete account (${response.code()})"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting account: ${e.message}"
                e.printStackTrace() // Add this to see full error in logs
            } finally {
                _isLoading.value = false
            }
        }
    }
}
