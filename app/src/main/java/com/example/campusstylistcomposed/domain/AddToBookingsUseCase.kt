package com.example.campusstylistcomposed.domain

class AddToBookingsUseCase (private val repository: RequestRepository) {
    suspend operator fun invoke(request: Request): Pair<String, String> {
        // Placeholder: No API call for now; just return request data for navigation
        return Pair(request.userName, request.service)
    }
}