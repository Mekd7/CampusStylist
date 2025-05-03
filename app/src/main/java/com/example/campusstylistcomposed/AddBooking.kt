package com.example.campusstylistcomposed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddBooking(
    token: String,
    onSaveClick: (String, String, String, String) -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var amPM by remember { mutableStateOf("") }
    var hairstyle by remember { mutableStateOf("") }

    val timeOptions = listOf("12:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00")
    val ampmOptions = listOf("AM", "PM")
    val hairstyleOptions = listOf("Straight", "Curly", "Wavy", "Braids", "Updo")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Booking",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "Time",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var expandedStart by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        modifier = Modifier.width(100.dp),
                        readOnly = true,
                        label = { Text("Start") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.Gray,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    DropdownMenu(
                        expanded = expandedStart,
                        onDismissRequest = { expandedStart = false },
                        modifier = Modifier.width(100.dp)
                    ) {
                        timeOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    startTime = option
                                    expandedStart = false
                                }
                            )
                        }
                    }
                }

                var expandedEnd by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        modifier = Modifier.width(100.dp),
                        readOnly = true,
                        label = { Text("End") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.Gray,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    DropdownMenu(
                        expanded = expandedEnd,
                        onDismissRequest = { expandedEnd = false },
                        modifier = Modifier.width(100.dp)
                    ) {
                        timeOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    endTime = option
                                    expandedEnd = false
                                }
                            )
                        }
                    }
                }
            }

            var expandedAMPM by remember { mutableStateOf(false) }
            Box {
                OutlinedTextField(
                    value = amPM,
                    onValueChange = { amPM = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    readOnly = true,
                    label = { Text("AM/PM") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )
                DropdownMenu(
                    expanded = expandedAMPM,
                    onDismissRequest = { expandedAMPM = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ampmOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                amPM = option
                                expandedAMPM = false
                            }
                        )
                    }
                }
            }

            var expandedHairstyle by remember { mutableStateOf(false) }
            Box {
                OutlinedTextField(
                    value = hairstyle,
                    onValueChange = { hairstyle = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    readOnly = true,
                    label = { Text("Hairstyle") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )
                DropdownMenu(
                    expanded = expandedHairstyle,
                    onDismissRequest = { expandedHairstyle = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    hairstyleOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                hairstyle = option
                                expandedHairstyle = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (startTime.isNotEmpty() && endTime.isNotEmpty() && amPM.isNotEmpty() && hairstyle.isNotEmpty()) {
                        onSaveClick(startTime, endTime, amPM, hairstyle)
                        onBackClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0136C)),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(0xFFE0136C))
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBookingPreview() {
    AddBooking(
        token = "preview_token",
        onSaveClick = { _, _, _, _ -> },
        onBackClick = {},
        onHomeClick = {},
        onProfileClick = {}
    )
}
