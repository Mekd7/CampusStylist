package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.campusstylistcomposed.ui.viewmodel.EditPostViewModel

@Composable
fun EditPostScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: EditPostViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Edit Post", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onBackClick(); navController.popBackStack() }) {
                Text("Back")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Save")
            }
        }
    }
}