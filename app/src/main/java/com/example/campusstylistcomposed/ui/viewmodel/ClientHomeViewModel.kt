package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.screens.Post

class ClientHomeViewModel : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    init {
        // Mock data for ClientHomePage
        _posts.addAll(
            listOf(
                Post("Ashley Gram", R.drawable.braid, "hairdresser1"),
                Post("Jane Doe", R.drawable.straight, "hairdresser2"),
                Post("Emma Smith", R.drawable.braid, "hairdresser3")
            )
        )
    }
}