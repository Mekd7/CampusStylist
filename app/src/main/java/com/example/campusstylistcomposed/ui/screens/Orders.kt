package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.booking.BookingViewModel

@Composable
fun OrderScreen(
    onBackClick: () -> Unit,
    viewModel: BookingViewModel = viewModel()
) {
    // Observe the orders list to ensure recomposition
    val orders by remember { derivedStateOf { viewModel.orders } }

    // Colors matching the screenshot
    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White
    val blackColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(pinkColor)
                .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Updated from ArrowLeft
                    contentDescription = "Back",
                    tint = whiteColor
                )
            }
            Text(
                text = "Orders",
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Hair dresser",
                color = whiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Service",
                color = whiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Status",
                color = whiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orders) { order ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.braid),
                            contentDescription = "Hairdresser Icon",
                            modifier = Modifier
                                .size(40.dp)
                                .background(whiteColor.copy(alpha = 0.2f), CircleShape)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = order.hairdresser,
                            color = whiteColor,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        text = order.service,
                        color = whiteColor,
                        fontSize = 16.sp
                    )
                    Text(
                        text = order.status,
                        color = whiteColor,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(pinkColor)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_today),
                    contentDescription = "Home",
                    tint = blackColor
                )
            }
            IconButton(onClick = { /* Already on Orders */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_view),
                    contentDescription = "Orders",
                    tint = blackColor
                )
            }
        }
    }
}