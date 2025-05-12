package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Post(val hairdresserName: String, val imageId: Int, val uploaderId: String) // VM Post

class HairDresserHomeViewModel : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    private val _hairdresserId = MutableStateFlow<String?>(null)
    val hairdresserId: StateFlow<String?> = _hairdresserId

    init {
        // Mock fetching the hairdresserId (replace with your actual logic)
        viewModelScope.launch {
            // Simulate fetching from a repository or local storage
            delay(500) // Simulate network delay
            _hairdresserId.value = "current_hairdresser_id_123" // Replace with actual ID retrieval
        }
        // Mock data for HairDresserHomeScreen
        _posts.addAll(
            listOf(
                Post("Featured Stylist 1", R.drawable.braid, "hairdresser1"),
                Post("Featured Stylist 2", R.drawable.straight, "hairdresser2"),
                Post("Featured Stylist 3", R.drawable.braid, "hairdresser3")
            )
        )
    }
}