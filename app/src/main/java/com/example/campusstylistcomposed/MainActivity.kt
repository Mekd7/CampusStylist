package com.example.campusstylistcomposed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.campusstylistcomposed.ui.theme.CampusStylistcomposedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CampusStylistcomposedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation() // Set up navigation
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "booking") {
        composable("booking") {
            BookingScreen(
                onBookingConfirmed = { navController.navigate("orders") }
            )
        }
        composable("orders") {
            OrderScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CampusStylistcomposedTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BookingScreen(
                onBookingConfirmed = {} // Empty lambda for preview
            )
        }
    }
}