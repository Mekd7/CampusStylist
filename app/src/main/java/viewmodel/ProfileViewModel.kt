package com.example.campusstylistcomposed.viewmodel




import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.Post
import com.example.campusstylistcomposed.R

class ProfileViewModel : ViewModel() {
    // State for posts
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    init {
        // Initialize with hardcoded posts (to be replaced with backend data later)
        _posts.addAll(
            listOf(
                Post(R.drawable.braid, "Description"),
                Post(R.drawable.braid, "Description"),
                Post(R.drawable.braid, "Description"),
                Post(R.drawable.braid, "Description")
            )
        )
    }

    // Event handlers (to be implemented later with backend integration)
    fun onEditProfile() {
        // Handle edit profile action (e.g., navigate to edit screen or update data)
    }

    fun onAddPost() {
        // Handle add post action (e.g., open a dialog to add a new post)
    }
}