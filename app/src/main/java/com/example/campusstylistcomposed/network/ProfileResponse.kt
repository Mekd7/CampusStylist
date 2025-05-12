package com.example.campusstylistcomposed.network

data class ProfileResponse(
    val username: String,
    val bio: String,
    val profilePictureUrl: String?
)