package com.example.campusstylistcomposed.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey("auth_token")
    private val roleKey = stringPreferencesKey("user_role")
    private val userIdKey = stringPreferencesKey("user_id")

    private val _token = MutableStateFlow<String?>(null)
    private val _role = MutableStateFlow<String?>(null)
    private val _userId = MutableStateFlow<String?>(null)

    val token: StateFlow<String?> = _token.asStateFlow()
    val role: StateFlow<String?> = _role.asStateFlow()
    val userId: StateFlow<String?> = _userId.asStateFlow()

    init {
        kotlinx.coroutines.runBlocking {
            val preferences = dataStore.data.firstOrNull()
            _token.value = preferences?.get(tokenKey)
            _role.value = preferences?.get(roleKey)
            _userId.value = preferences?.get(userIdKey)
        }
    }

    suspend fun saveToken(token: String, role: String, userId: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
            preferences[roleKey] = role
            preferences[userIdKey] = userId
        }

        _token.value = token
        _role.value = role
        _userId.value = userId
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
            preferences.remove(roleKey)
            preferences.remove(userIdKey)
        }

        _token.value = null
        _role.value = null
        _userId.value = null
    }

    fun getToken(): String? {
        return _token.value
    }

    fun getUserId(): String? {
        return _userId.value
    }

    fun getRole(): String? {
        return _role.value
    }
}