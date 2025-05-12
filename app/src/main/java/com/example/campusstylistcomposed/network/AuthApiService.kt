package com.example.campusstylistcomposed.network

import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.data.AuthResponse
import com.example.campusstylistcomposed.data.LoginRequest
import com.example.campusstylistcomposed.domain.Post
import com.example.campusstylistcomposed.domain.model.Booking
import com.example.campusstylistcomposed.domain.model.User


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
    @POST("auth/register")
    suspend fun signUp(@Body request: AuthRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/user/me")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfile

    @Multipart
    @POST("profile")
    suspend fun createProfile(
        @Part("username") username: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part profilePicture: MultipartBody.Part?,
        @Header("Authorization") authorization: String
    ): CreateProfileResponse

    @GET("profile")
    suspend fun getProfile(@Header("Authorization") authorization: String): ProfileResponse

    @GET("profile/{id}")
    suspend fun getProfile(
        @Path("id") id: Long,
        @Header("Authorization") authorization: String
    ): User

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
        @Path("userId") userId: Long
    ): List<Booking>

    @GET("posts/{hairdresserId}")
    suspend fun getPostsByHairdresserId(
        @Path("hairdresserId") hairdresserId: String,
        @Header("Authorization") token: String
    ): List<Post>
}