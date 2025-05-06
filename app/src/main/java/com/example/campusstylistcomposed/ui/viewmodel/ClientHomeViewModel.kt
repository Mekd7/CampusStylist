package com.example.campusstylistcomposed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.campusstylistcomposed.R


data class Stylist(
    val name: String,
    val imageResId: Int
)

class ClientHomeViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _stylists = MutableStateFlow<List<Stylist>>(emptyList())
    val stylists: StateFlow<List<Stylist>> = _stylists.asStateFlow()

    init {
        // Mock some stylist data
        _stylists.value = listOf(
            Stylist("Stylist 1", R.drawable.campstylist),
            Stylist("Stylist 2", R.drawable.campstylist),
            Stylist("Stylist 3", R.drawable.campstylist)
        )
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun logout(onLogout: () -> Unit) {
        _token.value = ""
        onLogout()
    }

    fun navigateToAddPost(onAddPostClick: (String) -> Unit) {
        onAddPostClick(_token.value)
    }

    fun navigateToEditProfile(onEditProfileClick: (String) -> Unit) {
        onEditProfileClick(_token.value)
    }

    fun navigateToAddBooking(onAddBookingClick: (String) -> Unit) {
        onAddBookingClick(_token.value)
    }
}