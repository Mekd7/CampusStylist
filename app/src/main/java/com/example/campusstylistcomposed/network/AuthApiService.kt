package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import com.example.campusstylistcomposed.data.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class UserProfile(
    val id: String,
    val email: String,
    val role: String,
    val hasCreatedProfile: Boolean
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun signUp(@Body request: AuthRequest): AuthResponse

    @GET("auth/user/me")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfile
}