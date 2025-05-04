package com.example.campusstylistcomposed


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun PostDetailScreen() {
    val darkBackgroundColor = Color(0xFF222020)
    val whiteColor = Color(0xFFFFFFFF)
    val pinkColor = Color(0xFFE0136C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Name
            Text(
                text = "Ashley Gram",
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 16.dp)
            )

            // Post Image
            Image(
                painter = painterResource(id = R.drawable.hair_style), // Use appropriate image resource
                contentDescription = "Knotless Goddess Braids",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Post Details
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Knotless Goddess Braids",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "16 inches",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Took 5 hours",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        // Bottom Navigation
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(pinkColor)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav_icon_3),
                contentDescription = "Home",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.nav_icon_4),
                contentDescription = "Info",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.nav_icon_2),
                contentDescription = "Calendar",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.nav_icon_1),
                contentDescription = "Profile",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    PostDetailScreen()
}