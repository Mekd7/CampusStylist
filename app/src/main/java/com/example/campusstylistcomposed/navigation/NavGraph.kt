package com.example.campusstylistcomposed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.campusstylistcomposed.ui.screens.ClientHomePage
import com.example.campusstylistcomposed.ui.screens.CreateProfileScreen
import com.example.campusstylistcomposed.ui.screens.HairDresserHomePage
import com.example.campusstylistcomposed.ui.screens.AddBookingScreen
import com.example.campusstylistcomposed.ui.screens.AddPostScreen
import com.example.campusstylistcomposed.ui.screens.EditBookingScreen
import com.example.campusstylistcomposed.ui.screens.EditPostScreen
import com.example.campusstylistcomposed.ui.screens.LoginScreen
import com.example.campusstylistcomposed.ui.screens.ManageScheduleScreen
import com.example.campusstylistcomposed.ui.screens.MyRequestsScreen
import com.example.campusstylistcomposed.ui.screens.ProfileScreen
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
                onAddPostClick = { navController.navigate("addPost/$token") },
                onEditProfileClick = { navController.navigate("profile/$token") },
                onAddBookingClick = { navController.navigate("addBooking/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "hairdresserHome/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HairDresserHomePage(
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onManageScheduleClick = { navController.navigate("manageSchedule/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "profile/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ProfileScreen(navController)
        }
        composable(
            "manageSchedule/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ManageScheduleScreen(navController)
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
        composable(
            "addPost/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AddPostScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onPostSuccess = { navController.popBackStack() }
            )
        }
        composable("myRequests") {
            MyRequestsScreen(navController)
        }
        composable("editBooking") {
            EditBookingScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("editPost") {
            EditPostScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}