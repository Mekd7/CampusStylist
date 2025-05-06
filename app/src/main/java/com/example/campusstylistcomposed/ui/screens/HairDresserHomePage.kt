package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel
import com.example.campusstylistcomposed.ui.viewmodel.Post
import com.example.campusstylistcomposed.R

@Composable
fun HairDresserHomePage(
    token: String,
    onLogout: () -> Unit,
    onManageScheduleClick: () -> Unit,
    viewModel: HairDresserHomeViewModel = viewModel()
) {
    LaunchedEffect(token) {
        viewModel.setToken(token)
    }

    val posts by viewModel.posts.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222020))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "CampusStylist!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            posts.forEach { post ->
                StylistCardFigma(post)
                Spacer(modifier = Modifier.height(24.dp))
            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(0xFFE0136C))
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Nav Action 1 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_1),
                    contentDescription = "Nav Icon 1",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { /* TODO: Nav Action 2 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_2),
                    contentDescription = "Nav Icon 2",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { viewModel.navigateToManageSchedule(onManageScheduleClick) }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_3),
                    contentDescription = "Nav Icon 3",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { viewModel.logout(onLogout) }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_4),
                    contentDescription = "Nav Icon 4",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun StylistCardFigma(post: Post) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "Stylist Profile",
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = post.stylistName,
                fontSize = 18.sp,
                color = Color(0xFFA7A3A3)
            )
        }

        Image(
            painter = painterResource(id = post.imageResId),
            contentDescription = "Hairstyle",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.33f)
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        TextButton(
            onClick = { /* TODO: Navigate to Profile */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFE0136C)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Visit Profile",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}