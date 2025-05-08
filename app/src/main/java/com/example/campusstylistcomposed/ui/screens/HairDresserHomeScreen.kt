package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel

@Composable
fun HairDresserHomePage(
    navController: NavHostController,
    token: String,
    viewModel: HairDresserHomeViewModel = viewModel()
) {
    val posts by viewModel.posts.collectAsState()

    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("editProfile/$token") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text("Edit Profile", color = whiteColor)
                }
                Button(
                    onClick = { navController.navigate("addPost/$token") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Post",
                        tint = whiteColor
                    )
                    Text("Add Post", color = whiteColor)
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(posts) { post ->
                    Image(
                        painter = painterResource(id = post.imageId),
                        contentDescription = post.serviceName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable {
                                navController.navigate("hairdresserPostDetail/$token/0/${post.serviceName}/${post.length}/${post.duration}")
                            }
                    )
                }
            }
        }
        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = { navController.navigate("hairdresserHome/$token") },
            onSecondaryClick = { navController.navigate("myRequests/$token") }, // Fixed to "myRequests"
            onTertiaryClick = { navController.navigate("manageSchedule/$token") },
            onProfileClick = { navController.navigate("hairdresserProfile/$token/0") }, // Assuming hairdresserId as 0 or dynamic
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}