package com.example.campusstylistcomposed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.campusstylistcomposed.domain.Request
import com.example.campusstylistcomposed.viewmodel.MyRequestsEvent
import com.example.campusstylistcomposed.viewmodel.MyRequestsUiState
import com.example.campusstylistcomposed.viewmodel.MyRequestsViewModel
import com.example.campusstylistcomposed.viewmodel.NavigationEvent

// My Requests Screen Composable
@Composable
fun MyRequestsScreen(navController: NavController, viewModel: MyRequestsViewModel = viewModel()) {
    // Observe the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Collect navigation events
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToAddBooking -> {
                    navController.navigate("addBooking/${event.userName}/${event.service}")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "My Requests",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Handle UI state
        when (uiState) {
            is MyRequestsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFE91E63))
                }
            }
            is MyRequestsUiState.Success -> {
                val requests = (uiState as MyRequestsUiState.Success).requests
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(requests) { request ->
                        RequestItem(
                            request = request,
                            onAddToBookings = {
                                viewModel.onEvent(MyRequestsEvent.AddToBookings(request))
                            },
                            onDecline = {
                                viewModel.onEvent(MyRequestsEvent.Decline(request))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            is MyRequestsUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as MyRequestsUiState.Error).message,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Bottom Navigation Bar (placeholder icons)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE91E63)) // Pink color
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_myplaces),
                contentDescription = "Home",
                tint = Color.White
            )
            Icon(
                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_search),
                contentDescription = "Search",
                tint = Color.White
            )
            Icon(
                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_today),
                contentDescription = "Calendar",
                tint = Color.White
            )
            Icon(
                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_manage),
                contentDescription = "Profile",
                tint = Color.White
            )
        }
    }
}

// Composable for each request item
@Composable
fun RequestItem(
    request: Request,
    onAddToBookings: () -> Unit,
    onDecline: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Request details
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = "${request.userName} has requested",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = request.service,
                color = Color.White,
                fontSize = 14.sp
            )
        }

        // Buttons
        Button(
            onClick = onAddToBookings,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)), // Pink color
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Add to Bookings", color = Color.White, fontSize = 12.sp)
        }
        Button(
            onClick = onDecline,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)), // Pink color
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Decline", color = Color.White, fontSize = 12.sp)
        }
    }
}

// Preview annotation for the MyRequestsScreen
@Preview(showBackground = true)
@Composable
fun MyRequestsScreenPreview() {
    MyRequestsScreen(navController = rememberNavController())
}