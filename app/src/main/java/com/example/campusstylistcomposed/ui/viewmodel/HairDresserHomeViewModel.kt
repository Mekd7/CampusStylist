package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.campusstylistcomposed.R

data class Post(
    val stylistName: String,
    val imageResId: Int
)

class HairDresserHomeViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        _posts.value = listOf(
            Post("You", R.drawable.hair_style),
            Post("You", R.drawable.hair_style),
            Post("You", R.drawable.hair_style)
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