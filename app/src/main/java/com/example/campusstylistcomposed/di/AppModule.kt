package com.example.campusstylistcomposed.di

import com.example.campusstylistcomposed.domain.DeclineRequestUseCase
import com.example.campusstylistcomposed.domain.GetRequestsUseCase
import com.example.campusstylistcomposed.domain.RequestRepository
import com.example.campusstylistcomposed.infrastructure.RequestApiService
import com.example.campusstylistcomposed.infrastructure.RequestRepositoryImpl
import com.example.campusstylistcomposed.domain.AddToBookingsUseCase
import com.example.campusstylistcomposed.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.20.10.7:8080") // Single source of Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRequestApiService(retrofit: Retrofit): RequestApiService {
        return retrofit.create(RequestApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(apiService: RequestApiService): RequestRepository {
        return RequestRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetRequestsUseCase(repository: RequestRepository): GetRequestsUseCase {
        return GetRequestsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddToBookingsUseCase(repository: RequestRepository): AddToBookingsUseCase {
        return AddToBookingsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeclineRequestUseCase(repository: RequestRepository): DeclineRequestUseCase {
        return DeclineRequestUseCase(repository)
    }
}