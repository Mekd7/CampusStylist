package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// Moved UserIdResponse before ApiService
data class UserIdResponse(
    val userId: String
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("auth/register")
    suspend fun signUp(@Body request: AuthRequest): AuthResponse

    @GET("auth/user/me")
    suspend fun getUserId(@Header("Authorization") token: String): UserIdResponse
}