package com.example.campusstylistcomposed.ui.screens

// Data class for a Service
data class Service(val name: String, val price: String, val iconId: Int)

// Data class for an Order
data class Order(
    val hairdresser: String = "Hairdresser",
    val service: String,
    val status: String = "APPROVED"
)

data class HairdresserPost(
    val imageId: Int,
    val serviceName: String,
    val length: String,
    val duration: String
)

