package com.example.campusstylistcomposed.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.ManageScheduleViewModel
import java.text.SimpleDateFormat
import java.util.*

data class Appointment(
    val time: String,
    val client: String,
    val service: String,
    val status: String = "APPROVED"
)

@Composable
fun ManageScheduleScreen(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onRequestsClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavHostController,
    viewModel: ManageScheduleViewModel = viewModel()
) {
    // Colors matching the BookingScreen
    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Manage Schedule",
                color = whiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AndroidView(
                factory = { context ->
                    CalendarView(context).apply {
                        setOnDateChangeListener { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            viewModel.onDateSelected(calendar.time)
                        }
                        firstDayOfWeek = Calendar.MONDAY
                        setBackgroundColor(pinkColor.hashCode())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("addBooking/$token") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text("Add Booking", color = whiteColor)
                }
                Button(
                    onClick = { navController.navigate("editBooking/$token") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkColor)
                ) {
                    Text("Edit Schedule", color = whiteColor)
                }
            }

            Text(
                text = "Appointments on ${SimpleDateFormat("d MMMM, yyyy", Locale.getDefault()).format(viewModel.selectedDate ?: Date())}",
                color = whiteColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            val appointments by viewModel.appointments // Observe the State directly
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment)
                }
            }

            Spacer(modifier = Modifier.height(60.dp)) // Space for footer
        }

        Footer(
            footerType = FooterType.HAIRDRESSER,
            onHomeClick = onHomeClick,
            onSecondaryClick = onRequestsClick,
            onTertiaryClick = onScheduleClick,
            onProfileClick = onProfileClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    val whiteColor = Color.White
    val pinkColor = Color(0xFFFF4081)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(pinkColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${appointment.time} - ${appointment.client}",
                color = whiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Service: ${appointment.service}",
                color = whiteColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Status: ${appointment.status}",
                color = whiteColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ManageScheduleScreenPreview() {
//    val viewModel = ManageScheduleViewModel().apply {
//        onDateSelected(Date()) // Ensure mock data is loaded
//    }
//
//    ManageScheduleScreen(
//        token = "mock_token",
//        onLogout = { /* Mock logout */ },
//        onHomeClick = { /* Mock home click */ },
//        onRequestsClick = { /* Mock requests click */ },
//        onScheduleClick = { /* Mock schedule click */ },
//        onProfileClick = { /* Mock profile click */ },
//        navController = navController, // Add mock NavController for preview
//        viewModel = viewModel
//    )
//}