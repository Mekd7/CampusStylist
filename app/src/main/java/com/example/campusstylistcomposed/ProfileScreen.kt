package com.example.campusstylistcomposed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.campusstylistcomposed.ui.theme.CampusStylistcomposedTheme
import com.example.campusstylistcomposed.viewmodel.ProfileViewModel

// Data class for a Post
data class Post(val imageId: Int, val description: String)

// Composable for the Profile Screen
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    // Colors matching the screenshot
    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Profile Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.braid),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .background(whiteColor.copy(alpha = 0.2f), CircleShape)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Profile Info
            Column {
                Text(
                    text = "Ashley Gram",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hair is my passion",
                    color = whiteColor,
                    fontSize = 14.sp
                )
                Text(
                    text = "Located at AAiT, dorm room 306",
                    color = whiteColor,
                    fontSize = 14.sp
                )
                Text(
                    text = "Book now!!!",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Buttons Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.onEditProfile() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor,
                    contentColor = whiteColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { viewModel.onAddPost() },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor,
                    contentColor = whiteColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Add Post",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Posts Section
        Text(
            text = "Posts",
            color = whiteColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.posts) { post ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = post.imageId),
                        contentDescription = post.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                    Text(
                        text = post.description,
                        color = whiteColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CampusStylistcomposedTheme {
        ProfileScreen()
    }
}