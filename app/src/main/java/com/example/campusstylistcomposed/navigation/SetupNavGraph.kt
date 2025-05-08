package com.example.campusstylistcomposed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.campusstylistcomposed.ui.screens.ClientHomePage
import com.example.campusstylistcomposed.ui.screens.ClientProfileScreen
import com.example.campusstylistcomposed.ui.screens.CreateProfileScreen
import com.example.campusstylistcomposed.ui.screens.HairDresserHomeScreen
import com.example.campusstylistcomposed.ui.screens.HairDresserProfileScreen
import com.example.campusstylistcomposed.ui.screens.HairDresserPostDetailScreen
import com.example.campusstylistcomposed.ui.screens.ManageScheduleScreen
import com.example.campusstylistcomposed.ui.screens.OrderScreen
import com.example.campusstylistcomposed.ui.screens.BookingScreen
import com.example.campusstylistcomposed.ui.screens.AddBookingScreen
import com.example.campusstylistcomposed.ui.screens.EditBookingScreen
import com.example.campusstylistcomposed.ui.screens.LoginScreen
import com.example.campusstylistcomposed.ui.screens.MyRequestsScreen
import com.example.campusstylistcomposed.ui.screens.SignUpScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.viewmodel.ClientHomeViewModel
import com.example.campusstylistcomposed.ui.viewmodel.CreateProfileViewModel
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate("signup") },
                onLoginSuccess = { token, isHairdresser ->
                    if (isHairdresser) {
                        navController.navigate("hairdresserHome/$token") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("clientHome/$token") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onSignupSuccess = { role, hasCreatedProfile, userId ->
                    val isHairdresser = role.uppercase() == "HAIRSTYLIST"
                    if (hasCreatedProfile) {
                        navController.navigate(if (isHairdresser) "hairdresserHome/$userId" else "clientHome/$userId") {
                            popUpTo("signup") { inclusive = true }
                        }
                    } else {
                        navController.navigate("createProfile/$userId/$isHairdresser") {
                            popUpTo("signup") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(
            "createProfile/{token}/{isHairdresser}",
            arguments = listOf(
                navArgument("token") { defaultValue = "" },
                navArgument("isHairdresser") { defaultValue = "false" }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val isHairdresser = backStackEntry.arguments?.getString("isHairdresser")?.toBoolean() ?: false
            CreateProfileScreen(
                token = token,
                isHairdresser = isHairdresser,
                onProfileCreated = {
                    navController.navigate(if (isHairdresser) "hairdresserHome/$token" else "clientHome/$token") {
                        popUpTo("createProfile/{token}/{isHairdresser}") { inclusive = true }
                    }
                },
                viewModel = viewModel()
            )
        }
        composable(
            "clientHome/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ClientHomePage(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { /* Already on home */ },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                onHairdresserProfileClick = { hairdresserId ->
                    navController.navigate("hairdresserProfile/$token/$hairdresserId")
                },
                viewModel = viewModel()
            )
        }
        composable(
            "hairdresserHome/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HairDresserHomeScreen(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { /* Already on home */ },
                onRequestsClick = { navController.navigate("myRequests/$token") },
                onScheduleClick = { navController.navigate("manageSchedule/$token") },
                onProfileClick = { navController.navigate("profile/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "hairdresserProfile/{token}/{hairdresserId}",
            arguments = listOf(
                navArgument("token") { defaultValue = "" },
                navArgument("hairdresserId") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val hairdresserId = backStackEntry.arguments?.getString("hairdresserId") ?: ""
            HairDresserProfileScreen(
                token = token,
                hairdresserId = hairdresserId,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                onPostClick = { post ->
                    navController.navigate("hairdresserPostDetail/$token/$hairdresserId/${post.imageId}/${post.serviceName}/${post.length}/${post.duration}")
                },
                onBookClick = { navController.navigate("booking/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "hairdresserPostDetail/{token}/{hairdresserId}/{imageId}/{serviceName}/{length}/{duration}",
            arguments = listOf(
                navArgument("token") { defaultValue = "" },
                navArgument("hairdresserId") { defaultValue = "" },
                navArgument("imageId") { defaultValue = "0" },
                navArgument("serviceName") { defaultValue = "" },
                navArgument("length") { defaultValue = "" },
                navArgument("duration") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val hairdresserId = backStackEntry.arguments?.getString("hairdresserId") ?: ""
            val imageId = backStackEntry.arguments?.getString("imageId")?.toIntOrNull() ?: 0
            val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
            val length = backStackEntry.arguments?.getString("length") ?: ""
            val duration = backStackEntry.arguments?.getString("duration") ?: ""
            HairDresserPostDetailScreen(
                token = token,
                hairdresserName = hairdresserId, // Replace with actual name if needed
                imageId = imageId,
                serviceName = serviceName,
                length = length,
                duration = duration,
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "clientProfile/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ClientProfileScreen(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { /* Already on profile */ },
                navigateToLogin = { navController.navigate("login") { popUpTo(0) { inclusive = true } } }
            )
        }
        composable(
            "orders/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            OrderScreen(
                token = token,
                onBackClick = { navController.popBackStack() },
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { /* Already on orders */ },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "booking/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            BookingScreen(
                token = token,
                onBookingConfirmed = { navController.navigate("orders/$token") },
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "manageSchedule/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ManageScheduleScreen(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { navController.navigate("hairdresserHome/$token") },
                onRequestsClick = { navController.navigate("myRequests/$token") },
                onScheduleClick = { /* Already on manage schedule */ },
                onProfileClick = { navController.navigate("profile/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "myRequests/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            MyRequestsScreen(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { navController.navigate("hairdresserHome/$token") },
                onRequestsClick = { /* Already on my requests */ },
                onScheduleClick = { navController.navigate("manageSchedule/$token") },
                onProfileClick = { navController.navigate("profile/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "addBooking/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AddBookingScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("editBooking") {
            EditBookingScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}