package com.example.campusstylistcomposed.infrastructure

import retrofit2.http.*

interface RequestApiService {
    @GET("requests")
    suspend fun getAllRequests(): List<RequestDto>

    @DELETE("requests/{id}")
    suspend fun deleteRequest(@Path("id") id: Int)
}