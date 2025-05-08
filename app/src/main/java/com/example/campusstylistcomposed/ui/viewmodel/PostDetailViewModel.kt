package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.screens.HairdresserPost

class PostDetailViewModel : ViewModel() {
    private val _posts = mutableStateListOf<HairdresserPost>()
    val posts: List<HairdresserPost> get() = _posts

    init {
        // Mock data for testing
        _posts.addAll(
            listOf(
                HairdresserPost(R.drawable.braid, "Knotless Goddess Braids", "16 inches", "5 hours"),
                HairdresserPost(R.drawable.straight, "Straight", "Long", "3 hours")
            )
        )
    }

    fun getPostByServiceName(serviceName: String): HairdresserPost? {
        return _posts.find { it.serviceName == serviceName }
    }

    fun deletePost(serviceName: String) {
        _posts.removeAll { it.serviceName == serviceName }
    }
}