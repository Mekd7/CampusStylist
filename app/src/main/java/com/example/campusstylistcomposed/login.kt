package com.example.campusstylistcomposed

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Keep this import
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll // Keep this import
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// You might need to adjust the import if LoginScreen is in a different file
// import com.example.hairdresser.ui.theme.* // Example if you have a theme file



@Composable
fun LoginScreen() {
    // --- State Variables ---
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- Colors (Same as SignUpScreen) ---
    val pinkColor = Color(0xFFE0136C)
    val darkColor = Color(0xFF222020)
    val grayColor = Color(0xFFA7A3A3)

    // --- UI Structure ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColor)
    ) {
        // --- Fixed Wave Background (Same as SignUpScreen) ---
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Adjust height if needed based on design
        ) {
            val width = size.width
            val height = size.height

            val wavePath = Path().apply {
                moveTo(0f, 0f)
                lineTo(width, 0f)
                lineTo(width, height * 0.7f)
                cubicTo(
                    width * 0.75f, height * 1.05f,
                    width * 0.25f, height * 0.85f,
                    0f, height * 0.95f
                )
                close()
            }

            drawPath(
                path = wavePath,
                color = pinkColor
            )
        }

        // --- Scrollable Content Column (Same structure as SignUpScreen) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Enable scrolling
                .padding(horizontal = 24.dp, vertical = 24.dp), // Padding for content
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer to push content below the wave
            Spacer(modifier = Modifier.height(180.dp))

            // --- Updated Header Text ---
            Text(
                text = "Welcome back", // Changed text
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Login to CampusStylist", // Changed text
                style = TextStyle(
                    color = grayColor,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start)
            )

            // --- Email Field (Same as SignUpScreen) ---
            Text(
                text = "Email",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Password Field (Same as SignUpScreen) ---
            Text(
                text = "Password",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Updated Login Button ---
            Button(
                onClick = { /* TODO: Handle login logic */ }, // Action for login
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor // Same pink color
                ),
                shape = RoundedCornerShape(50) // Same rounded shape
            ) {
                Text(
                    text = "Login", // Changed button text
                    fontSize = 18.sp
                )
            }

            // Spacer before the bottom link
            Spacer(modifier = Modifier.height(32.dp)) // Adjust spacing as needed

            // --- Updated Bottom Link ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ", // Changed text
                    style = TextStyle(
                        color = grayColor,
                        fontSize = 16.sp
                    )
                )

                Text(
                    text = "SIGN UP", // Changed text
                    style = TextStyle(
                        color = pinkColor, // Same pink color for link
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 4.dp)
                    // .clickable { /* TODO: Navigate to Sign Up Screen */ } // Add click listener for navigation
                )
            }

            // Padding at the bottom of the scrollable content
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}