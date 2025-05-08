package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.ClientHomeViewModel

data class Post(
    val hairdresserName: String,
    val imageId: Int,
    val hairdresserId: String
)

@Composable
fun ClientHomePage(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHairdresserProfileClick: (String) -> Unit,
    viewModel: ClientHomeViewModel = viewModel()
) {
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
                .padding(top = 16.dp)
        ) {
            // Header with title
            Text(
                text = "Home",
                color = whiteColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        color = pinkColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(12.dp))
                            .background(color = Color(0xFF2A3435), shape = RoundedCornerShape(12.dp))
                            .clickable { /* Navigate to HairDresserProfileScreen */ },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3435)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = post.imageId),
                                    contentDescription = "${post.hairdresserName} Post",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(end = 12.dp)
                                )
                                Column {
                                    Text(
                                        text = post.hairdresserName,
                                        color = whiteColor,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "View Profile",
                                        color = pinkColor,
                                        fontSize = 14.sp,
                                        modifier = Modifier.clickable {
                                            onHairdresserProfileClick(post.hairdresserId)
                                        }
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = pinkColor,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(pinkColor.copy(alpha = 0.1f))
                                    .padding(4.dp)
                                    .clickable {
                                        onHairdresserProfileClick(post.hairdresserId)
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Footer(
                footerType = FooterType.CLIENT,
                onHomeClick = onHomeClick,
                onSecondaryClick = onOrdersClick,
                onTertiaryClick = onProfileClick,
                onProfileClick = onProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF2A3435),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientHomePagePreview() {
    val viewModel = ClientHomeViewModel()
    ClientHomePage(
        token = "mock_token",
        onLogout = { /* Mock logout */ },
        onHomeClick = { /* Mock home click */ },
        onOrdersClick = { /* Mock orders click */ },
        onProfileClick = { /* Mock profile click */ },
        onHairdresserProfileClick = { /* Mock hairdresser profile click */ },
        viewModel = viewModel
    )
}