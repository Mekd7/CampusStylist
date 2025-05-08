package com.example.campusstylistcomposed.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.screens.HairdresserPost

class HairDresserProfileViewModel : ViewModel() {
    private val _hairdresserName = mutableStateOf("")
    val hairdresserName: String get() = _hairdresserName.value

    private val _posts = mutableStateListOf<HairdresserPost>()
    val posts: List<HairdresserPost> get() = _posts

    fun setHairdresserData(hairdresserId: String) {
        // Mock data based on hairdresserId
        when (hairdresserId) {
            "hairdresser1" -> {
                _hairdresserName.value = "Ashley Gram"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.braid, "Braid", "Medium", "2 hours"),
                        HairdresserPost(R.drawable.straight, "Straight", "Long", "3 hours")
                    )
                )
            }
            "hairdresser2" -> {
                _hairdresserName.value = "Jane Doe"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.straight, "Straight", "Short", "1.5 hours"),
                        HairdresserPost(R.drawable.braid, "Braid", "Long", "2.5 hours")
                    )
                )
            }
            "hairdresser3" -> {
                _hairdresserName.value = "Emma Smith"
                _posts.clear()
                _posts.addAll(
                    listOf(
                        HairdresserPost(R.drawable.braid, "Braid", "Short", "1 hour"),
                        HairdresserPost(R.drawable.straight, "Straight", "Medium", "2 hours")
                    )
                )
            }
        }
    }
}

