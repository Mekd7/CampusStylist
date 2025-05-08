package viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Data class to hold the UI state
data class ProfileUiState(
    val name: String = "Ashley Gram",
    val serviceTitle: String = "Knotless Goddess Braids",
    val serviceDetails: String = "16 INCHES Took 5 hours"
)

// Sealed class to define UI events
sealed class ProfileUiEvent {
    object OnBackClicked : ProfileUiEvent()
    object OnPostReelClicked : ProfileUiEvent()
    object OnUpdatePostClicked : ProfileUiEvent()
}

class ProfileViewModel : ViewModel() {
    // State flow to hold the UI state
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    // Handle UI events
    fun handleEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.OnBackClicked -> {
                // Handle back button click (e.g., navigate back)
            }
            is ProfileUiEvent.OnPostReelClicked -> {
                // Handle post reel button click (e.g., trigger posting logic)
            }
            is ProfileUiEvent.OnUpdatePostClicked -> {
                // Handle update post button click (e.g., trigger update logic)
            }
        }
    }
}
