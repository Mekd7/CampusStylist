package com.example.campusstylistcomposed.domain

interface RequestRepository {
    suspend fun getAllRequests(): List<Request>
    suspend fun deleteRequest(id: Int)
}