package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import com.example.campusstylistcomposed.data.LoginRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class UserProfile(
    val id: String,
    val email: String,
    val role: String,
    val hasCreatedProfile: Boolean
)

data class CreateProfileResponse(
    val message: String
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun signUp(@Body request: AuthRequest): AuthResponse

    @GET("auth/user/me")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfile

    @Multipart
    @POST("profile")
    suspend fun createProfile(
        @Part username: MultipartBody.Part,
        @Part bio: MultipartBody.Part,
        @Part profilePicture: MultipartBody.Part?
    ): CreateProfileResponse

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): CreateProfileResponse
}