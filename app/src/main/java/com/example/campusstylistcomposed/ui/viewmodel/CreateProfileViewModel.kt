package com.example.campusstylistcomposed.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusstylistcomposed.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Create text parts
                val usernameBody = _name.value.toRequestBody("text/plain".toMediaTypeOrNull())
                val bioBody = _bio.value.toRequestBody("text/plain".toMediaTypeOrNull())

                // Create image part if available
                val profilePicturePart = _selectedImageUri.value?.let { uri ->
                    val file = UriToFileConverter.convertUriToFile(uri, context)
                    file?.let {
                        val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData(
                            "profilePicture",
                            it.name,
                            requestBody
                        )
                    }
                }

                // Make the API call
                val response = apiService.createProfile(
                    token = "Bearer ${_token.value}", // Add Bearer prefix here
                    username = usernameBody,
                    bio = bioBody,
                    profilePicture = profilePicturePart
                )

                if (response.message == "Profile created successfully") {
                    onSuccess()
                } else {
                    _errorMessage.value = response.message ?: "Failed to create profile"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error creating profile: ${e.localizedMessage ?: "Unknown error"}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    object UriToFileConverter {
        fun convertUriToFile(uri: Uri, context: Context): File? {
            val contentResolver = context.contentResolver
            val fileName = getFileName(uri, contentResolver) ?: "profile_${System.currentTimeMillis()}.jpg"
            val tempFile = File.createTempFile("temp_", fileName, context.cacheDir)

            return try {
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                tempFile
            } catch (e: Exception) {
                e.printStackTrace()
                tempFile.delete()
                null
            }
        }

        private fun getFileName(uri: Uri, contentResolver: android.content.ContentResolver): String? {
            return when (uri.scheme) {
                "content" -> {
                    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                        } else null
                    }
                }
                "file" -> File(uri.path).name
                else -> null
            }
        }
    }
}