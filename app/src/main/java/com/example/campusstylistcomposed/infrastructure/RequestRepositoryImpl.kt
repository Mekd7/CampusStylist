package com.example.campusstylistcomposed.infrastructure

import com.example.campusstylistcomposed.domain.Request
import com.example.campusstylistcomposed.domain.RequestRepository

class RequestRepositoryImpl(
    private val apiService: RequestApiService
) : RequestRepository {
    override suspend fun getAllRequests(): List<Request> {
        return apiService.getAllRequests().map { dto ->
            Request(
                id = dto.id,
                userName = dto.userName,
                service = dto.service
            )
        }
    }

    override suspend fun deleteRequest(id: Int) {
        apiService.deleteRequest(id)
    }
}