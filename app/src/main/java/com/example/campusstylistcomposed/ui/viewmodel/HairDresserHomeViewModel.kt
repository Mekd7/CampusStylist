package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.screens.HairdresserPost

class HairDresserHomeViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _posts = MutableStateFlow<List<HairdresserPost>>(emptyList())
    val posts: StateFlow<List<HairdresserPost>> = _posts.asStateFlow()

    init {
        _posts.value = listOf(
            HairdresserPost(R.drawable.hair_style, "Braids", "16 inches", "5 hours"),
            HairdresserPost(R.drawable.hair_style, "Weave", "20 inches", "4 hours"),
            HairdresserPost(R.drawable.hair_style, "Straight", "Long", "3 hours")
        )
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun logout(onLogout: () -> Unit) {
        _token.value = ""
        onLogout()
    }

    fun navigateToHome(onHomeClick: () -> Unit) {
        onHomeClick()
    }

    fun navigateToRequests(onRequestsClick: () -> Unit) {
        onRequestsClick()
    }

    fun navigateToSchedule(onScheduleClick: () -> Unit) {
        onScheduleClick()
    }

    fun navigateToProfile(onProfileClick: () -> Unit) {
        onProfileClick()
    }

    fun navigateToAddPost(onNavigate: () -> Unit) {
        onNavigate()
    }
}