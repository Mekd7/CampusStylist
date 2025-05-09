package com.example.campusstylistcomposed.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.campusstylistcomposed.domain.AddToBookingsUseCase
import com.example.campusstylistcomposed.domain.DeclineRequestUseCase
import com.example.campusstylistcomposed.domain.GetRequestsUseCase
import com.example.campusstylistcomposed.domain.RequestRepository
import com.example.campusstylistcomposed.infrastructure.RequestApiService
import com.example.campusstylistcomposed.infrastructure.RequestRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth_prefs")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideRequestApiService(retrofit: Retrofit): RequestApiService {
        return retrofit.create(RequestApiService::class.java)
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