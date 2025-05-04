package com.example.campusstylistcomposed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.campusstylistcomposed.ui.theme.CampusStylistcomposedTheme
import com.example.campusstylistcomposed.viewmodel.MyRequestsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate("signup") },
                onLoginSuccess = { token, isHairstylist ->
                    if (isHairstylist) {
                        navController.navigate("hairstylistHomePage/$token") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("clientHomePage/$token") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onSignupSuccess = { token, isHairstylist ->
                    if (isHairstylist) {
                        navController.navigate("hairstylistHomePage/$token") {
                            popUpTo("signup") { inclusive = true }
                        }
                    } else {
                        navController.navigate("clientHomePage/$token") {
                            popUpTo("signup") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(
            route = "clientHomePage/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ClientHomePage(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("clientHomePage/{token}") { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "hairstylistHomePage/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HairstylistHomePage(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("hairstylistHomePage/{token}") { inclusive = true }
                    }
                },
                onNavigateToMyRequests = { navController.navigate("myRequests") }
            )
        }
        composable("orders") {
            OrderScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("myRequests") {
            val viewModel: MyRequestsViewModel = viewModel()
            MyRequestsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = "addBooking/{userName}/{service}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("service") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val service = backStackEntry.arguments?.getString("service") ?: ""
            // Placeholder for AddBookingScreen
            Text("Add Booking: $userName, $service - Adjust time/date here later")
        }
    }
}