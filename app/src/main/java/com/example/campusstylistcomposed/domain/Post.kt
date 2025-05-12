package com.example.campusstylistcomposed.domain


import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long? = null,
    val userId: Long,
    val pictureUrl: String,
    val description: String
)