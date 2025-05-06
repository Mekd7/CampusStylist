package com.example.campusstylistcomposed.ui.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class EditPostUiState(
    val description: String = "Existing post description", // Example default
    val image: String? = null
)

class EditPostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EditPostUiState())
    val uiState: StateFlow<EditPostUiState> = _uiState

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onImageSelected(image: String?) {
        _uiState.value = _uiState.value.copy(image = image)
    }
}