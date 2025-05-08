package com.example.campusstylistcomposed.network

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Removed provideRetrofit() and provideApiService() as they are now in AppModule
    // Add other non-conflicting provisions here if needed
}