package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("signup")
    suspend fun signup(@Body request: AuthRequest): AuthResponse

    @POST("login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}