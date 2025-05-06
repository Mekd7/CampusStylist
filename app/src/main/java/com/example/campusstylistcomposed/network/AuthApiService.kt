package com.example.campusstylistcomposed.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("hasCreatedProfile") val hasCreatedProfile: Boolean
)

data class SignUpRequest(
    val username: String,
    val password: String,
    val role: String
)

data class SignUpResponse(
    val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("hasCreatedProfile") val hasCreatedProfile: Boolean
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse
}