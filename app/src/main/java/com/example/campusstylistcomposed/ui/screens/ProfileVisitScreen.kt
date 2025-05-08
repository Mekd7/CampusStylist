package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType

@Composable
fun ProfileVisitScreen(
    token: String,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBookClick: () -> Unit
) {
    // Colors from Figma (using provided globalVars)
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
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp) // Space for bottom nav
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image (Ellipse 93:125)
                Image(
                    painter = painterResource(id = R.drawable.hair_style),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Profile Name (Text 93:126)
                Text(
                    text = "Ashley Gram",
                    color = whiteColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Bio (Text 93:127)
                Text(
                    text = "Hair is my passion\n\nLocated At AAiT, dorm room 306\n\nBook Now !!!",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Book Button (Rectangle 93:128, Text 93:130)
                Button(
                    onClick = { onBookClick() },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text(
                        text = "Book",
                        color = whiteColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Posts Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Posts Title (Text 93:141)
                Text(
                    text = "Posts",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
                )

                // Grid for Posts (using Rows for simplicity, consider LazyVerticalGrid for many items)
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Row 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                    ProfilePostItem(imageRes = R.drawable.hair_style, description = "Description")
                }
            }
        }

        // Bottom Navigation using Footer component
        Footer(
            footerType = FooterType.CLIENT,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = onProfileClick,
            onProfileClick = onProfileClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(pinkColor)
                .padding(vertical = 12.dp)
        )
    }
}

@Composable
fun ProfilePostItem(imageRes: Int, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Hair Post",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
        )
    }
}
