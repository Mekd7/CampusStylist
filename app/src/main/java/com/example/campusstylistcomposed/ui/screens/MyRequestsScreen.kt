package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.components.Footer
import com.example.campusstylistcomposed.ui.components.FooterType
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel

@Composable
fun MyRequestsScreen(
    token: String,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onRequestsClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HairDresserHomeViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222020))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Requests Screen",
                color = Color.White,
                fontSize = 20.sp
            )
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