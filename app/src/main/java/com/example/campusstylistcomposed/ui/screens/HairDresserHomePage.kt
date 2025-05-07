package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel
import com.example.campusstylistcomposed.ui.viewmodel.Post
import com.example.campusstylistcomposed.R

@Composable
fun HairDresserHomePage(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onRequestsClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onProfileClick: () -> Unit,
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

        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = onHomeClick,
            onSecondaryClick = onRequestsClick,
            onTertiaryClick = onScheduleClick,
            onProfileClick = onProfileClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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

@Preview(showBackground = true)
@Composable
fun HairDresserHomePagePreview() {
    val viewModel = HairDresserHomeViewModel().apply {
        setToken("mock_token")
    }

    HairDresserHomePage(
        token = "mock_token",
        onLogout = {},
        onHomeClick = {},
        onRequestsClick = {},
        onScheduleClick = {},
        onProfileClick = {},
        viewModel = viewModel
    )
}