package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.data.repository.AuthRepository
import com.example.campusstylistcomposed.network.ApiService
import com.example.campusstylistcomposed.domain.model.User
import com.example.campusstylistcomposed.domain.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HairDresserProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProfile(token: String, hairdresserId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val userId = authRepository.userId.value?.toLongOrNull()
                    ?: throw IllegalStateException("User ID not found")
                val userResponse = apiService.getProfile(userId, "Bearer $token")
                val postsResponse = apiService.getPostsByHairdresserId(hairdresserId, "Bearer $token")
                _user.value = userResponse
                _posts.value = postsResponse
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
                        onSuccess()
                    }
                } else {
                    authRepository.clearToken()
                    onSuccess()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error during logout: ${e.message ?: "Unknown error"}"
                authRepository.clearToken()
                onSuccess()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun handleLogoutError(response: Response<Unit>) {
        _errorMessage.value = when (response.code()) {
            401 -> "Invalid token. Please log in again."
            else -> "Logout failed: ${response.message()}"
        }
    }

    fun deleteAccount(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.logout("Bearer $token") // Reuse logout for simplicity
                if (response.isSuccessful) {
                    authRepository.clearToken()
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to delete account: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting account: ${e.message ?: "Unknown error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}