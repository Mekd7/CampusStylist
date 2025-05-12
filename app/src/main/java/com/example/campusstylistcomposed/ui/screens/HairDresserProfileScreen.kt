package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.campusstylistcomposed.domain.Post
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserProfileViewModel

@Composable
fun HairDresserProfileScreen(
    token: String,
    hairdresserId: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPostClick: (Post) -> Unit,
    navController: (String) -> Unit,
    viewModel: HairDresserProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchProfile(token, hairdresserId)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val user by viewModel.user.collectAsState()
    val posts by viewModel.posts.collectAsState()

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
                    .padding(16.dp)
            ) {
                // Header
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
                        .size(80.dp)
                        .clip(CircleShape)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = buildString {
                        append(user?.bio ?: "Hairdresser bio")
                        append("\nLocated at AAI, dorm room 306")
                        append("\nBook Now !!!")
                    },
                    color = whiteColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController("editProfile/$token") },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                        shape = CircleShape
                    ) {
                        Text("Edit Profile", fontSize = 14.sp)
                    }
                    Button(
                        onClick = { navController("addPost/$token") },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                        shape = CircleShape
                    ) {
                        Text("Add Post", fontSize = 14.sp)
                    }
                }
                Button(
                    onClick = {
                        viewModel.logout {
                            onLogout()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                    shape = CircleShape,
                    enabled = !isLoading
                ) {
                    Text("Logout", fontSize = 14.sp)
                }
                Button(
                    onClick = {
                        viewModel.deleteAccount(token) {
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
                    Text("Delete Account", fontSize = 14.sp)
                }

                errorMessage?.let {
                    Text(
                        text = "Error: $it",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Posts
                Text(
                    text = "Posts",
                    color = whiteColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(posts) { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onPostClick(post) },
                            colors = CardDefaults.cardColors(containerColor = Color.Black)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                AsyncImage(
                                    model = post.pictureUrl ?: "https://via.placeholder.com/150",
                                    contentDescription = post.description,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = post.description ?: "Description",
                                    color = whiteColor,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = { navController("manageSchedule/$token") },
            onProfileClick = onProfileClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}