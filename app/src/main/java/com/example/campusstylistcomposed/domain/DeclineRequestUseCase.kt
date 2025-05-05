package com.example.campusstylistcomposed.domain

class DeclineRequestUseCase (private val repository: RequestRepository) {
    suspend operator fun invoke(request: Request) {
        repository.deleteRequest(request.id)
    }
}