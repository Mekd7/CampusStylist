package com.example.campusstylistcomposed.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CreateProfileViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _isHairdresser = MutableStateFlow(false)
    val isHairdresser: StateFlow<Boolean> = _isHairdresser.asStateFlow()

    fun setInitialData(token: String, isHairdresser: Boolean) {
        _token.value = token
        _isHairdresser.value = isHairdresser
    }

    fun updateName(value: String) { _name.value = value }
    fun updateBio(value: String) { _bio.value = value }

    fun createProfile(onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Simulate API call with a delay
            delay(1000) // Simulate network delay
            // Mock profile creation logic
            if (_name.value.isNotBlank() && _bio.value.isNotBlank()) {
                onSuccess()
            } else {
                // Handle error (e.g., show a message in the UI)
            }
        }
    }
}