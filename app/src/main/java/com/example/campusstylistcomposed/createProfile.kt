package com.example.campusstylistcomposed



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    name = "Create Profile Screen",
    heightDp = 800
)
@Composable
fun CreateProfileScreenPreview() {
    CreateProfileScreen()
}

@Composable
fun CreateProfileScreen() {
    // State for input fields
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    // Colors from Figma
    val darkBackgroundColor = Color(0xFF222020)
    val pinkColor = Color(0xFFE0136C)
    val whiteColor = Color(0xFFFFFFFF)
    val placeholderGrayColor = Color(0xFFD9D9D9)
    val textGrayColor = Color(0xFFA7A3A3) // Assuming a gray for placeholder text if needed

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Back Arrow and Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Arrow Button (using a simple Canvas triangle for now)
                IconButton(
                    onClick = { /* TODO: Handle back navigation */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = Path().apply {
                            moveTo(size.width, 0f)
                            lineTo(0f, size.height / 2)
                            lineTo(size.width, size.height)
                            close() // Ensure it's closed for filling if needed, though stroke is common
                        }
                        // Using white for better contrast than D9D9D9
                        drawPath(path, color = whiteColor)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Create Profile",
                    color = whiteColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black // 900 maps to Black
                )
            }

            // Profile Picture Section
            Box(
                modifier = Modifier
                    .size(120.dp) // Adjust size as needed
                    .clip(CircleShape)
                    .background(placeholderGrayColor)
                    .clickable { /* TODO: Handle image upload */ },
                contentAlignment = Alignment.Center
            ) {
                // Placeholder Icon or Text if needed, or leave empty
                // Example: Icon(Icons.Default.Person, contentDescription = "Profile Placeholder", tint = darkBackgroundColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Upload picture",
                color = pinkColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal, // 400 maps to Normal
                modifier = Modifier.clickable { /* TODO: Handle image upload */ }
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Your Name Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Your Name",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = TextStyle(
                        color = whiteColor,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true,
                    cursorBrush = SolidColor(whiteColor)
                )
                Divider(color = pinkColor, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bio Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Bio",
                    color = whiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    textStyle = TextStyle(
                        color = whiteColor,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp) // Allow multiple lines for bio
                        .padding(vertical = 8.dp),
                    cursorBrush = SolidColor(whiteColor)
                    // Consider adding maxLines = 4 or similar
                )
                Divider(color = pinkColor, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(64.dp)) // More space before button

            // Create Profile Button
            Button(
                onClick = { /* TODO: Handle profile creation logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp), // Match Figma border radius
                colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
            ) {
                Text(
                    text = "Create Profile",
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black // 900 maps to Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // Padding at the bottom
        }
    }
}
