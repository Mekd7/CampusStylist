package com.example.campusstylistcomposed


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
// Removed Material Icons imports as we are using drawables now
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.Home
// import androidx.compose.material.icons.filled.ShoppingCart
// import androidx.compose.material3.Icon // Replaced by Image
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
import androidx.compose.ui.text.font.FontStyle // Keep for potential future use, though not used in title now
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Renamed the main composable to reflect the file name
@Composable
fun HairDresserHomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222020)) // Background color from Figma
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // App title - Styled according to Figma
            Text(
                text = "CampusStylist!",
                fontSize = 25.sp, // Updated font size from Figma
                fontStyle = FontStyle.Normal, // Removed Italic
                fontWeight = FontWeight.Bold, // Updated font weight from Figma (700 maps to Bold)
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Stylist entries - Using the updated StylistCard composable
            repeat(3) { // Assuming 3 cards as in the reference file
                StylistCardFigma() // Use the Figma-styled card
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Extra space at bottom for navigation bar
            Spacer(modifier = Modifier.height(60.dp)) // Keep spacing for nav bar overlap
        }

        // Bottom navigation - Styled according to Figma
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                // Apply rounded corners only to the top, matching Figma's Rectangle 21 shape
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // Adjust radius if needed
                .background(Color(0xFFE0136C)) // Background color from Figma
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // Changed arrangement for 4 icons
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Using downloaded PNGs for icons
            IconButton(onClick = { /* TODO: Nav Action 1 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_1),
                    contentDescription = "Nav Icon 1",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 2 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_2),
                    contentDescription = "Nav Icon 2",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 3 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_3),
                    contentDescription = "Nav Icon 3",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }

            IconButton(onClick = { /* TODO: Nav Action 4 */ }) {
                Image(
                    painter = painterResource(id = R.drawable.nav_icon_4),
                    contentDescription = "Nav Icon 4",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }
        }
    }
}

// Renamed StylistCard to avoid conflict and reflect Figma styling
@Composable
fun StylistCardFigma() {
    Column {
        // Stylist info row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // Profile icon - Using downloaded image
            Box(
                modifier = Modifier
                    .size(32.dp) // Size from reference, adjust if needed based on Figma visual
                    .background(Color.White, CircleShape), // White background like reference
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon), // Updated image resource
                    contentDescription = "Stylist Profile",
                    modifier = Modifier.fillMaxSize().clip(CircleShape) // Ensure image is clipped to circle
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Stylist name - Styled according to Figma
            Text(
                text = "Stylist Name",
                fontSize = 18.sp, // Updated font size from Figma
                color = Color(0xFFA7A3A3) // Color matches Figma
            )
        }

        // Hair image - Using downloaded image
        Image(
            painter = painterResource(id = R.drawable.hair_style), // Updated image resource
            contentDescription = "Hairstyle",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.33f) // Maintain aspect ratio or adjust as needed
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(8.dp)) // Added slight rounding to image corners
        )

        // Visit profile button - Styled according to Figma
        TextButton(
            onClick = { /* TODO: Navigate to Profile */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp) // Height from reference, adjust if needed
                .clip(RoundedCornerShape(30.dp)) // Updated corner radius from Figma (30px)
                .background(Color(0xFFE0136C)), // Background color from Figma
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Visit Profile",
                color = Color.White,
                fontSize = 20.sp, // Updated font size from Figma
                fontWeight = FontWeight.SemiBold // Updated font weight (600 maps roughly to SemiBold)
            )
        }
    }
}
