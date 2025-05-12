package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import com.example.campusstylistcomposed.data.LoginRequest
import com.example.campusstylistcomposed.domain.model.Booking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.Response

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
    @POST("profile") // Replace with your actual API endpoint
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part profilePicture: MultipartBody.Part? // Corrected: No part name in annotation
    ): CreateProfileResponse

    @POST("/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    @POST("bookings")
    suspend fun createBooking(@Body booking: Booking): Booking

    @GET("bookings")
    suspend fun getBookingsByHairstylistDate(
        @Query("hairstylistId") hairstylistId: Long,
        @Query("date") date: String
    ): List<Booking>

    @GET("bookings/{userId}")
    suspend fun getBookingsByUserId(
        @Header("Authorization") token: String,
        @Query("userId") userId: Long // Ensure this is Long, not String
    ): List<Booking>
}