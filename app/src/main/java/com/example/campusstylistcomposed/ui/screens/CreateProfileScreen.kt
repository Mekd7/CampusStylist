package com.example.campusstylistcomposed.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.campusstylistcomposed.ui.viewmodel.CreateProfileViewModel

@Preview(
    showBackground = true,
    name = "Create Profile Screen",
    heightDp = 800
)
@Composable
fun CreateProfileScreenPreview() {
    CreateProfileScreen(isHairdresser = false, token = "", onProfileCreated = {})
}

@Composable
fun CreateProfileScreen(
    isHairdresser: Boolean,
    token: String,
    onProfileCreated: () -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onImageSelected(uri) }
    )

    // Initialize ViewModel with isHairdresser, token, and image picker
    LaunchedEffect(Unit) {
        viewModel.setInitialData(isHairdresser, token)
        viewModel.initializeImagePicker(imagePickerLauncher)
    }

    // State synchronization
    var name by remember { mutableStateOf(viewModel.name.value) }
    var bio by remember { mutableStateOf(viewModel.bio.value) }
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()

    val darkBackgroundColor = Color(0xFF222020)
    val pinkColor = Color(0xFFE0136C)
    val whiteColor = Color(0xFFFFFFFF)
    val placeholderGrayColor = Color(0xFFD9D9D9)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* TODO: Handle back navigation */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = Path().apply {
                            moveTo(size.width, 0f)
                            lineTo(0f, size.height / 2)
                            lineTo(size.width, size.height)
                            close()
                        }
                        drawPath(path, color = whiteColor)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Create Profile",
                    color = whiteColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(if (selectedImageUri != null) Color.Transparent else placeholderGrayColor)
                    .clickable { viewModel.pickImage() },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected profile picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Upload picture",
                color = pinkColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickable { viewModel.pickImage() }
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Your Name",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = name,
                    onValueChange = { name = it; viewModel.updateName(it) },
                    textStyle = TextStyle(
                        color = whiteColor,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true,
                    cursorBrush = SolidColor(whiteColor)
                )
                Divider(color = pinkColor, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Bio",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = bio,
                    onValueChange = { bio = it; viewModel.updateBio(it) },
                    textStyle = TextStyle(
                        color = whiteColor,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp)
                        .padding(vertical = 8.dp),
                    cursorBrush = SolidColor(whiteColor)
                )
                Divider(color = pinkColor, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(color = pinkColor)
            } else {
                Button(
                    onClick = {
                        viewModel.createProfile(onSuccess = onProfileCreated)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .height(56.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text(
                        text = "Create Profile",
                        color = whiteColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}