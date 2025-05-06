package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val name: String = "Ashley Gram",
    val bio: String = "Hair is my passion\nLocated at AAIT, dorm room 306\nBook Now !!!",
    val picture: String? = null
)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onBioChanged(bio: String) {
        _uiState.value = _uiState.value.copy(bio = bio)
    }

    fun onClearPicture() {
        _uiState.value = _uiState.value.copy(picture = null)
    }
}



