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
import androidx.compose.ui.tooling.preview.Preview
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType

@Composable
fun HairDresserPostDetailScreen(
    token: String,
    hairdresserName: String,
    imageId: Int,
    serviceName: String,
    length: String,
    duration: String,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit
) {
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
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = whiteColor
                )
            }
            Text(
                text = hairdresserName,
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Image(
                painter = painterResource(id = imageId),
                contentDescription = serviceName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Service: $serviceName",
                color = whiteColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Length: $length",
                color = whiteColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Duration: $duration",
                color = whiteColor,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f))
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
fun HairDresserPostDetailScreenPreview() {
    HairDresserPostDetailScreen(
        token = "mock_token",
        hairdresserName = "Ashley Gram",
        imageId = R.drawable.braid,
        serviceName = "Braid",
        length = "Medium",
        duration = "2 hours",
        onHomeClick = { /* Mock home click */ },
        onOrdersClick = { /* Mock orders click */ },
        onProfileClick = { /* Mock profile click */ },
        onBackClick = { /* Mock back click */ }
    )
}