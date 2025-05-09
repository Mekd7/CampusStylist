package com.example.campusstylistcomposed.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.network.ApiService
import com.example.campusstylistcomposed.ui.screens.HairdresserPost
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HairDresserProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _hairdresserName = mutableStateOf("")
    val hairdresserName: String get() = _hairdresserName.value

    private val _posts = mutableStateListOf<HairdresserPost>()
    val posts: List<HairdresserPost> get() = _posts

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setHairdresserData(hairdresserId: String) {
        // Mock data based on hairdresserId
        when (hairdresserId) {
            "hairdresser1" -> {
                _hairdresserName.value = "Ashley Gram"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.braid, "Braid", "Medium", "2 hours"),
                        HairdresserPost(R.drawable.straight, "Straight", "Long", "3 hours")
                    )
                )
            }
            "hairdresser2" -> {
                _hairdresserName.value = "Jane Doe"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.straight, "Straight", "Short", "1.5 hours"),
                        HairdresserPost(R.drawable.braid, "Braid", "Long", "2.5 hours")
                    )
                )
            }
            "hairdresser3" -> {
                _hairdresserName.value = "Emma Smith"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.braid, "Braid", "Short", "1 hour"),
                        HairdresserPost(R.drawable.straight, "Straight", "Medium", "2 hours")
                    )
                )
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", null)
                val authHeader = if (token.isNullOrEmpty()) "" else "Bearer $token"
                val response = apiService.logout(authHeader)
                if (response.message.contains("success", ignoreCase = true)) {
                    clearSession()
                    onSuccess()
                } else {
                    _errorMessage.value = "Logout failed: ${response.message}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error during logout: ${e.message ?: "Unknown error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun clearSession() {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("auth_token").apply()
    }
}