package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.viewmodel.EditBookingViewModel

@Composable
fun EditBookingScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: EditBookingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Edit Booking", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.date,
            onValueChange = { viewModel.onDateChanged(it) },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.time,
            onValueChange = { viewModel.onTimeChanged(it) },
            label = { Text("Time") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onBackClick() }) {
                Text("Back")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Save")
            }
        }
    }
}