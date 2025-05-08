package com.example.campusstylistcomposed.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import java.text.SimpleDateFormat
import java.util.*
import com.example.campusstylistcomposed.R
import com.example.campusstylistcomposed.ui.viewmodel.BookingViewModel

@Composable
fun BookingScreen(
    token: String,
    onBookingConfirmed: () -> Unit,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: BookingViewModel = viewModel()
) {
    // Observe navigation signal
    val navigateToOrders by remember { derivedStateOf { viewModel.navigateToOrders } }
    LaunchedEffect(navigateToOrders) {
        if (navigateToOrders) {
            onBookingConfirmed()
            viewModel.onNavigated()
        }
    }

    // State for the list of services
    val services = remember {
        listOf(
            Service("Braid", "30Birr", R.drawable.braid),
            Service("Paystra", "150Birr", R.drawable.straight),
            Service("Weave", "80Birr", R.drawable.braid),
            Service("Relaxer", "120Birr", R.drawable.straight),
        )
    }

    // Colors matching the screenshot
    val backgroundColor = Color(0xFF1C2526)
    val pinkColor = Color(0xFFFF4081)
    val whiteColor = Color.White
    val blackColor = Color.Black

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
                text = "Book",
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

            Text(
                text = if (viewModel.selectedService == null) "Select a Service" else "Select a Date",
                color = whiteColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
                    .clickable(enabled = viewModel.selectedService != null) {
                        viewModel.clearSelection()
                    },
                textAlign = TextAlign.Center
            )

            if (viewModel.selectedService == null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(services) { service ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onServiceSelected(service) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = service.iconId),
                                contentDescription = "${service.name} Icon",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 16.dp)
                                    .background(whiteColor.copy(alpha = 0.2f), CircleShape)
                                    .padding(8.dp)
                            )
                            Text(
                                text = service.name,
                                color = whiteColor,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = service.price,
                                color = whiteColor,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            if (viewModel.selectedService != null && viewModel.selectedDate != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(pinkColor, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your service: ${viewModel.selectedService?.name}",
                        color = whiteColor,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Date: ${SimpleDateFormat("d MMMM, yyyy", Locale.getDefault()).format(viewModel.selectedDate!!)}",
                        color = whiteColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Total price: ${viewModel.selectedService?.price}",
                        color = whiteColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Button(
                        onClick = { viewModel.confirmBooking() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blackColor,
                            contentColor = whiteColor
                        )
                    ) {
                        Text(
                            text = "Confirm Booking",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Footer(
            footerType = FooterType.CLIENT,
            onHomeClick = onHomeClick,
            onSecondaryClick = onOrdersClick,
            onTertiaryClick = onProfileClick,
            onProfileClick = onProfileClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

