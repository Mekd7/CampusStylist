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
                    if (isHairdresser) navController.navigate("hairdresserHome/$token")
                    else navController.navigate("clientHome/$token")
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onSignupSuccess = { token, isHairdresser ->
                    navController.navigate("createProfile/$token/$isHairdresser")
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
                onProfileCreated = { navController.navigate(if (isHairdresser) "hairdresserHome/$token" else "clientHome/$token") },
                viewModel = viewModel()
            )
        }
        composable("clientHome/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ClientHomePage(
                token = token,
                onLogout = { navController.navigate("login") },
                onAddPostClick = { token -> navController.navigate("addPost") },
                onEditProfileClick = { token -> navController.navigate("profile") },
                onAddBookingClick = { token -> navController.navigate("addBooking") },
                viewModel = viewModel()
            )
        }
        composable("hairdresserHome/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HairDresserHomePage(
                token = token,
                onLogout = { navController.navigate("login") },
                onManageScheduleClick = { navController.navigate("manageSchedule") },
                viewModel = viewModel()
            )
        }
        composable("profile") { ProfileScreen(navController) }
        composable("manageSchedule") { ManageScheduleScreen(navController) }
        composable("myRequests") { MyRequestsScreen(navController) }
        composable("addBooking") { AddBookingScreen(navController, onBackClick = { navController.popBackStack() }) }
        composable("editBooking") { EditBookingScreen(navController, onBackClick = { navController.popBackStack() }) }
        composable("addPost") { AddPostScreen(navController, onBackClick = { navController.popBackStack() }, onPostSuccess = { navController.popBackStack() }) }
        composable("editPost") { EditPostScreen(navController, onBackClick = { navController.popBackStack() }) }
    }
}