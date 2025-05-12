package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserProfileViewModel

@Composable
fun HairDresserProfileScreen(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: (String) -> Unit,
    viewModel: HairDresserProfileViewModel = hiltViewModel()
) {
    // Initialize ViewModel with token
    LaunchedEffect(Unit) {
        viewModel.fetchProfile(token)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val user by viewModel.user.collectAsState()

    val backgroundColor = Color(0xFF222020)
    val pinkColor = Color(0xFFE0136C)
    val whiteColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = pinkColor,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user?.username ?: "Hairdresser name",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                AsyncImage(
                    model = user?.profilePicture ?: "https://via.placeholder.com/100",
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = user?.bio ?: "Hairdresser bio",
                    color = whiteColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController("editProfile/$token") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                    shape = CircleShape,
                    enabled = !isLoading
                ) {
                    Text("Edit Profile", fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        viewModel.logout {
                            onLogout()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                    shape = CircleShape,
                    enabled = !isLoading
                ) {
                    Text("Log out", fontSize = 16.sp)
                }

                errorMessage?.let {
                    Text(
                        text = "Error: $it",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = onProfileClick,
            onProfileClick = onProfileClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}