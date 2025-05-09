package com.example.campusstylistcomposed.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthRepository @Inject constructor(private val context: Context) {
    private val tokenKey = stringPreferencesKey("auth_token")

    // Use MutableStateFlow to hold the token value
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    init {
        // Load the initial token value from DataStore into StateFlow
        kotlinx.coroutines.runBlocking {
            val initialToken = context.dataStore.data.firstOrNull()?.get(tokenKey)
            _token.value = initialToken
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
        _token.value = token // Update StateFlow
    }

    fun getToken(): StateFlow<String?> {
        return token
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
        _token.value = null // Update StateFlow
    }
}