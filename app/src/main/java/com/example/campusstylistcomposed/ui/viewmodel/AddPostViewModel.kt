
package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AddPostUiState(
    val description: String = "",
    val image: String? = null
)

class AddPostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddPostUiState())
    val uiState: StateFlow<AddPostUiState> = _uiState

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onImageSelected(image: String?) {
        _uiState.value = _uiState.value.copy(image = image)
    }
}