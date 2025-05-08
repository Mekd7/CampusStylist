package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.R
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
    onPostClick: (HairdresserPost) -> Unit,
    onBookClick: () -> Unit,
    viewModel: HairDresserProfileViewModel = viewModel()
) {
    LaunchedEffect(hairdresserId) {
        viewModel.setHairdresserData(hairdresserId)
    }

    val hairdresserName by remember { derivedStateOf { viewModel.hairdresserName } }
    val posts by remember { derivedStateOf { viewModel.posts } }

    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White
    val blackColor = Color.Black

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
            Text(
                text = hairdresserName,
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkColor,
                        contentColor = whiteColor
                    )
                ) {
                    Text("Logout")
                }
                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkColor,
                        contentColor = whiteColor
                    )
                ) {
                    Text("Book")
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
                            .clickable { onPostClick(post) }
                    )
                }
            }
        }

        Footer(
            footerType = FooterType.CLIENT,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = onProfileClick,
            onProfileClick = onProfileClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HairDresserProfileScreenPreview() {
    val viewModel = HairDresserProfileViewModel().apply {
        setHairdresserData("hairdresser1")
    }
    HairDresserProfileScreen(
        token = "mock_token",
        hairdresserId = "hairdresser1",
        onLogout = { /* Mock logout */ },
        onHomeClick = { /* Mock home click */ },
        onOrdersClick = { /* Mock orders click */ },
        onProfileClick = { /* Mock profile click */ },
        onPostClick = { /* Mock post click */ },
        onBookClick = { /* Mock book click */ },
        viewModel = viewModel
    )
}