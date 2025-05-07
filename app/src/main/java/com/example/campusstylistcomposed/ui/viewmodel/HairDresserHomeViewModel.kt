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
        // Mock some post data
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

    fun navigateToManageSchedule(onNavigate: () -> Unit) {
        onNavigate()
    }

    fun navigateToAddPost(onNavigate: () -> Unit) {
        onNavigate()
    }
}