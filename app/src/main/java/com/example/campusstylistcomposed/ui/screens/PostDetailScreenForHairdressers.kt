package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.viewmodel.PostDetailViewModel

@Composable
fun PostDetailScreenForHairdressers(
    navController: NavHostController,
    token: String,
    serviceName: String,
    viewModel: PostDetailViewModel = viewModel()
) {
    val post by remember { derivedStateOf { viewModel.getPostByServiceName(serviceName) } }

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
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = whiteColor
                )
            }
            Text(
                text = "Ashley Gram",
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = painterResource(id = post?.imageId ?: R.drawable.braid),
                contentDescription = serviceName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Text(
                text = serviceName,
                color = whiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "16 inches",
                color = whiteColor,
                fontSize = 16.sp
            )
            Text(
                text = "Took 5 hours",
                color = whiteColor,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("editPost/$token/$serviceName") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text("Edit Post", color = whiteColor)
                }
                Button(
                    onClick = { viewModel.deletePost(serviceName); navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text("Delete Post", color = whiteColor)
                }
            }
        }
        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = { navController.navigate("hairdresserHome/$token") },
            onSecondaryClick = { navController.navigate("requests/$token") }, // Assuming Requests screen
            onTertiaryClick = { navController.navigate("manageSchedule/$token") },
            onProfileClick = { navController.navigate("editProfile/$token") },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}