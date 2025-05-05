package com.example.campusstylistcomposed.domain

class GetRequestsUseCase (private val repository: RequestRepository) {
    suspend operator fun invoke(): List<Request> {
        return repository.getAllRequests()
    }
}