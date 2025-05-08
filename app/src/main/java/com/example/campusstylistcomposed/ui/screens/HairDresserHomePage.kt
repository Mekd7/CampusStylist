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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HairdresserHomeScreen(
    navController: NavHostController,
    token: String,
    viewModel: HairDresserHomeViewModel = viewModel()
) {
    val posts by viewModel.posts.collectAsState() // Observe StateFlow directly

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
                            .clickable { navController.navigate("postDetailForHairdressers/${token}/${post.serviceName}") }
                    )
                }
            }
        }
        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = { navController.navigate("hairdresserHome/$token") },
            onSecondaryClick = { navController.navigate("requests/$token") },
            onTertiaryClick = { navController.navigate("manageSchedule/$token") },
            onProfileClick = { navController.navigate("editProfile/$token") },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}