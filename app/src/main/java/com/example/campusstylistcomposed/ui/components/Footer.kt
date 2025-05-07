package com.example.campusstylistcomposed.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.campusstylistcomposed.R

enum class FooterType {
    CLIENT, HAIRDRESSER
}

@Composable
fun Footer(
    footerType: FooterType,
    onHomeClick: () -> Unit,
    onSecondaryClick: () -> Unit, // Orders for Client, Requests for Hairdresser
    onTertiaryClick: () -> Unit,  // Profile for Client, Schedule for Hairdresser
    onProfileClick: () -> Unit,   // Only for Hairdresser
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color(0xFFE0136C))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Icon
        IconButton(onClick = onHomeClick) {
            Icon(
                painter = painterResource(id = R.drawable.nav_icon_3),
                contentDescription = "Home",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        when (footerType) {
            FooterType.CLIENT -> {
                // Orders Icon
                IconButton(onClick = onSecondaryClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_icon_5),
                        contentDescription = "Orders",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Profile Icon
                IconButton(onClick = onTertiaryClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_icon_1),
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            FooterType.HAIRDRESSER -> {
                // Requests Icon
                IconButton(onClick = onSecondaryClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_icon_4),
                        contentDescription = "Requests",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Schedule Icon
                IconButton(onClick = onTertiaryClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_icon_2),
                        contentDescription = "Manage Schedule",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Profile Icon
                IconButton(onClick = onProfileClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_icon_1),
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}