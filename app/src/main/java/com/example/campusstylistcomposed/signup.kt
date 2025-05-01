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
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val pinkColor = Color(0xFFE0136C)
    val darkColor = Color(0xFF222020)
    val grayColor = Color(0xFFA7A3A3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColor)
    ) {
        // Wave background - Stays fixed, does not scroll
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

        // Scrollable Content Column
        // Add verticalScroll modifier here
        Column(
            modifier = Modifier
                .fillMaxSize() // Takes available space within the Box
                .verticalScroll(rememberScrollState()) // Makes the content scrollable
                .padding(horizontal = 24.dp, vertical = 24.dp), // Apply padding *inside* the scrollable area
            // verticalArrangement = Arrangement.spacedBy(16.dp) // Can keep or use Spacers, Spacers are often clearer in scrollable content
            horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
        ) {
            // Adjust this spacer to position content below the wave
            // Ensure it doesn't overlap badly on small screens before scrolling
            Spacer(modifier = Modifier.height(180.dp)) // Pushes content down below the wave

            // Welcome text
            Text(
                text = "Welcome!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start) // Align text to start
            )

            Spacer(modifier = Modifier.height(8.dp)) // Added spacer for breathing room

            Text(
                text = "Create an account to join CampusStylist",
                style = TextStyle(
                    color = grayColor,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start) // Align text to start
            )

            // Email field Label
            Text(
                text = "Email",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start) // Align text to start
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth() // Fill width within padding
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

            // Password field Label
            Text(
                text = "Password",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start) // Align text to start
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth() // Fill width within padding
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

            // Sign up buttons
            Button(
                onClick = { /* Handle client sign up */ },
                modifier = Modifier
                    .fillMaxWidth() // Fill width within padding
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Sign Up as client",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Reduced spacer

            Button(
                onClick = { /* Handle hair dresser sign up */ },
                modifier = Modifier
                    .fillMaxWidth() // Fill width within padding
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Sign Up as hair dresser",
                    fontSize = 18.sp
                )
            }

            // Use a Spacer to push the "Sign In" link towards the bottom
            // Adjust height as needed, or add more content above
            Spacer(modifier = Modifier.height(32.dp)) // Provides space before the final row

            // Sign in link - now at the end of the scrollable content
            Row(
                modifier = Modifier.fillMaxWidth(), // Fill width within padding
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    style = TextStyle(
                        color = grayColor,
                        fontSize = 16.sp
                    )
                )

                Text(
                    text = "SIGN IN",
                    style = TextStyle(
                        color = pinkColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    // Consider adding clickable modifier here later
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Add some padding at the very bottom of the scrollable content
            // ensures the last element isn't stuck at the absolute edge when scrolled down.
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}