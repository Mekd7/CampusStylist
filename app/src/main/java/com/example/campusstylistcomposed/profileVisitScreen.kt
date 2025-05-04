package com.example.campusstylistcomposed



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
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
                    painter = painterResource(id = R.drawable.hair_style), // Use downloaded image
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
                    fontWeight = FontWeight.Black // 900 maps to Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Bio (Text 93:127)
                Text(
                    text = "Hair is my passion\n\nLocated At AAiT, dorm room 306\n\nBook Now !!!",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black, // 900 maps to Black
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp // Adjust line height for spacing
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Book Button (Rectangle 93:128, Text 93:130)
                Button(
                    onClick = { /* TODO: Handle Book action */ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Adjust width as needed
                        .height(50.dp),
                    shape = RoundedCornerShape(30.dp), // Match Figma border radius
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text(
                        text = "Book",
                        color = whiteColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black // 900 maps to Black
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
                    fontWeight = FontWeight.Black, // 900 maps to Black
                    modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
                )

                // Grid for Posts (using Rows for simplicity, consider LazyVerticalGrid for many items)
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:131, 93:146
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:133, 93:147
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:135, 93:149
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:137, 93:151
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Row 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:142, 93:153
                    PostItem(imageRes = R.drawable.hair_style, description = "Description") // 93:144, 93:155
                }
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
            IconButton(onClick = { /* TODO: Nav Action 1 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_3),
                    contentDescription = "Nav Icon 1",
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 2 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_4),
                    contentDescription = "Nav Icon 2",
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 3 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_2),
                    contentDescription = "Nav Icon 3",
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 4 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_1),
                    contentDescription = "Nav Icon 4",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
fun PostItem(imageRes: Int, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min) // Wrap content width
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Hair Post",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp) // Adjust size as needed
                .clip(RoundedCornerShape(8.dp)) // Optional rounding
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black // 900 maps to Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}