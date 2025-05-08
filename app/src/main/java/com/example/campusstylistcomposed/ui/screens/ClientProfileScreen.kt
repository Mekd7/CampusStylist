package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.layout.ContentScale
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.R

@Composable
fun ClientProfileScreen(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    navigateToLogin: (String) -> Unit
) {
    val backgroundColor = Color(0xFF222020)
    val pinkColor = Color(0xFFE0136C)
    val whiteColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "User name",
                color = whiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.profile_icon), // Replace with actual profile image resource
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .padding(top = 8.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Client bio",
                color = whiteColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Handle delete account logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                shape = CircleShape
            ) {
                Text("Delete Account", fontSize = 16.sp)
            }

            Button(
                onClick = { navigateToLogin(token) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = pinkColor, contentColor = whiteColor),
                shape = CircleShape
            ) {
                Text("Log out", fontSize = 16.sp)
            }
        }

        Footer(
            footerType = FooterType.CLIENT,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = onProfileClick,
            onProfileClick = onProfileClick, // Tertiary click is Profile for client
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClientProfileScreenPreview() {
    ClientProfileScreen(
        token = "mock_token",
        onLogout = { /* Mock logout */ },
        onHomeClick = { /* Mock home click */ },
        onOrdersClick = { /* Mock orders click */ },
        onProfileClick = { /* Mock profile click */ },
        navigateToLogin = { /* Mock navigate to login */ }
    )
}