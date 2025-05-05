package com.example.campusstylistcomposed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Home



@Composable
fun ClientHomePage(
    token: String,
    onLogout: () -> Unit,
    onAddPostClick: (String) -> Unit,
    onEditProfileClick: (String) -> Unit,
    onAddBookingClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222020))
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // App title
            Text(
                text = "CampusStylist!",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Placeholder StylistCard entries
            repeat(3) {
                StylistCard()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Extra space at bottom for navigation bar
            Spacer(modifier = Modifier.height(60.dp))
        }

        // Bottom navigation with logout
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
            IconButton(onClick = { /* Already on Home screen */ }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { onAddBookingClick(token) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Booking",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { onEditProfileClick(token) }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onLogout) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun StylistCard() {
    Column {
        // Stylist info row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // Profile icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_2),
                    contentDescription = "Stylist Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Stylist name
            Text(
                text = "Stylist Name",
                fontSize = 16.sp,
                color = Color(0xFFA7A3A3)
            )
        }

        // Hair image
        Image(
            painter = painterResource(id = R.drawable.campstylist),
            contentDescription = "Hairstyle",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.33f)
                .padding(bottom = 12.dp)
        )

        // Visit profile button
        TextButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0136C)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Visit Profile",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}