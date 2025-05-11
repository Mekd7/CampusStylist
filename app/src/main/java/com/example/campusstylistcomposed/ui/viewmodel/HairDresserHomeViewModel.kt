package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.screens.Post

class HairDresserHomeViewModel : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    init {
        // Mock data for HairDresserHomeScreen (e.g., featured posts or own posts)
        _posts.addAll(
            listOf(
                Post("Featured Stylist 1", R.drawable.braid, "hairdresser1"),
                Post("Featured Stylist 2", R.drawable.straight, "hairdresser2"),
                Post("Featured Stylist 3", R.drawable.braid, "hairdresser3")
            )
        )
    }
}