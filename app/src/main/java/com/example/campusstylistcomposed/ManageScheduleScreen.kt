package com.example.campusstylistcomposed

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.theme.CampusStylistcomposedTheme
import com.example.campusstylistcomposed.viewmodel.ScheduleViewModel
import java.util.*

@Composable
fun ManageScheduleScreen(viewModel: ScheduleViewModel = viewModel()) {
    // Colors matching the screenshot
    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White
    val blackColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Manage Schedule",
            color = whiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Calendar View
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
                    dateTextAppearance = android.R.style.TextAppearance_Medium
                    weekDayTextAppearance = android.R.style.TextAppearance_Small
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Buttons Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.onAddBooking() },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor,
                    contentColor = whiteColor
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Add Booking",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { viewModel.onEditSchedule() },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pinkColor,
                    contentColor = whiteColor
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Edit Schedule",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer
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
            IconButton(onClick = { /* Navigate to Chat */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_send),
                    contentDescription = "Chat",
                    tint = blackColor
                )
            }
            IconButton(onClick = { /* Already on Schedule */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_month),
                    contentDescription = "Schedule",
                    tint = whiteColor
                )
            }
            IconButton(onClick = { /* Navigate to Profile */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                    contentDescription = "Profile",
                    tint = blackColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageScheduleScreenPreview() {
    CampusStylistcomposedTheme {
        ManageScheduleScreen()
    }
}