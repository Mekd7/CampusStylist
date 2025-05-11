package com.example.campusstylistcomposed.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.data.CreateProfileResponse // Import the response class
import com.example.campusstylistcomposed.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _isHairdresser = MutableStateFlow(false)
    val isHairdresser: StateFlow<Boolean> = _isHairdresser.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()

    lateinit var imagePickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>

    fun setInitialData(token: String, isHairdresser: Boolean) {
        _token.value = token
        _isHairdresser.value = isHairdresser
        println("Set token: $token") // Debug log
    }

    fun updateName(value: String) { _name.value = value }
    fun updateBio(value: String) { _bio.value = value }

    fun initializeImagePicker(launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
        imagePickerLauncher = launcher
    }

    fun pickImage() {
        imagePickerLauncher.launch(
            PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
        )
    }

    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri
        println("Selected image URI: $uri") // Debug log
    }

    fun createProfile(onSuccess: () -> Unit) {
        if (_name.value.isBlank()) {
            _errorMessage.value = "Name cannot be empty"
            return
        }
        if (_bio.value.isBlank()) {
            _errorMessage.value = "Bio cannot be empty"
            return
        }
        if (_selectedImageUri.value == null) {
            _errorMessage.value = "Please select a profile picture"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Create RequestBody for text fields using RequestBody.create
                val usernameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), _name.value)
                val bioBody = RequestBody.create("text/plain".toMediaTypeOrNull(), _bio.value)
                val profilePicturePart = _selectedImageUri.value?.let { uri ->
                    val file = UriToFileConverter.convertUriToFile(uri, context)
                    file?.let {
                        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), it)
                        MultipartBody.Part.createFormData("profilePicture", it.name, requestBody)
                    }
                }

                println("Sending request: username=${_name.value}, bio=${_bio.value}, profilePicture=${profilePicturePart?.body?.contentType()}")
                val response = apiService.createProfile(
                    token = "Bearer ${_token.value}",
                    username = usernameBody,
                    bio = bioBody,
                    profilePicture = profilePicturePart
                )
                if (response.message == "Profile created successfully") {
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to create profile: ${response.message}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error creating profile: ${e.message}"
                println("Error: ${e.message}") // Debug log
            } finally {
                _isLoading.value = false
            }
        }
    }

    object UriToFileConverter {
        @Suppress("DEPRECATION") // For older Android versions, adjust as needed
        fun convertUriToFile(uri: Uri, context: Context): File? {
            val contentResolver = context.contentResolver
            val fileName = getFileName(uri, contentResolver) ?: "image_${System.currentTimeMillis()}.jpg"
            val cacheDir = context.cacheDir
            val tempFile = File(cacheDir, fileName)

            return try {
                contentResolver.openInputStream(uri)?.let { inputStream ->
                    inputStream.use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    tempFile
                } ?: run {
                    tempFile.delete()
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                tempFile.delete()
                null
            }
        }

        private fun getFileName(uri: Uri, contentResolver: android.content.ContentResolver): String? {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        return it.getString(nameIndex)
                    }
                }
            }
            return null
        }
    }
}