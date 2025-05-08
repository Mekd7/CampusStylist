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
import com.example.campusstylistcomposed.ui.screens.ProfileVisitScreen
import com.example.campusstylistcomposed.ui.screens.PostDetailScreen
import com.example.campusstylistcomposed.ui.screens.EditProfile
import com.example.campusstylistcomposed.ui.screens.AddPostScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campusstylistcomposed.ui.viewmodel.ClientHomeViewModel
import com.example.campusstylistcomposed.ui.viewmodel.HairDresserHomeViewModel
import com.example.campusstylistcomposed.ui.viewmodel.ManageScheduleViewModel
import com.example.campusstylistcomposed.ui.viewmodel.BookingViewModel
import java.net.URLEncoder
import java.net.URLDecoder

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
                        if (isHairdresser) {
                            navController.navigate("hairdresserProfile/$userId/$userId") {
                                popUpTo("signup") { inclusive = true }
                            }
                        } else {
                            navController.navigate("clientHome/$userId") {
                                popUpTo("signup") { inclusive = true }
                            }
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
                    if (isHairdresser) {
                        navController.navigate("hairdresserProfile/$token/$token") {
                            popUpTo("createProfile/{token}/{isHairdresser}") { inclusive = true }
                        }
                    } else {
                        navController.navigate("clientHome/$token") {
                            popUpTo("createProfile/{token}/{isHairdresser}") { inclusive = true }
                        }
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
                    navController.navigate("profileVisit/$token/$hairdresserId")
                },
                viewModel = viewModel<ClientHomeViewModel>()
            )
        }
        composable(
            "hairdresserHome/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val hairDresserHomeViewModel: HairDresserHomeViewModel = viewModel()
            HairDresserHomeScreen(
                navController = navController,
                token = token,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = { /* Already on home */ },
                onRequestsClick = { navController.navigate("myRequests/$token") },
                onScheduleClick = { navController.navigate("manageSchedule/$token") },
                onProfileClick = { navController.navigate("hairdresserProfile/$token/$token") }, // Navigate to profile on profile icon click
                viewModel = hairDresserHomeViewModel
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
                onHomeClick = { navController.navigate("hairdresserHome/$token") },
                onOrdersClick = { navController.navigate("myRequests/$token") },
                onProfileClick = { /* Already on profile */ },
                onPostClick = { post ->
                    val encodedServiceName = URLEncoder.encode(post.serviceName, "UTF-8")
                    val encodedLength = URLEncoder.encode(post.length, "UTF-8")
                    val encodedDuration = URLEncoder.encode(post.duration, "UTF-8")
                    navController.navigate("hairdresserPostDetail/$token/$hairdresserId/${post.imageId}/$encodedServiceName/$encodedLength/$encodedDuration")
                },
                onBookClick = { /* Not applicable */ },
                navController = { route -> navController.navigate(route) },
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
            val serviceName = URLDecoder.decode(backStackEntry.arguments?.getString("serviceName") ?: "", "UTF-8")
            val length = URLDecoder.decode(backStackEntry.arguments?.getString("length") ?: "", "UTF-8")
            val duration = URLDecoder.decode(backStackEntry.arguments?.getString("duration") ?: "", "UTF-8")
            HairDresserPostDetailScreen(
                token = token,
                hairdresserName = hairdresserId,
                imageId = imageId,
                serviceName = serviceName,
                length = length,
                duration = duration,
                onHomeClick = { navController.navigate("hairdresserHome/$token") },
                onOrdersClick = { navController.navigate("myRequests/$token") },
                onProfileClick = { navController.navigate("hairdresserProfile/$token/$hairdresserId") },
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
                navigateToLogin = { navController.navigate("login") { popUpTo(0) { inclusive = true } } },
                onEditProfileClick = { navController.navigate("editProfile/$token") }
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
                onProfileClick = { navController.navigate("hairdresserProfile/$token/$token") },
                navController = { route -> navController.navigate(route) },
                viewModel = viewModel<ManageScheduleViewModel>()
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
                onProfileClick = { navController.navigate("hairdresserProfile/$token/$token") },
                viewModel = viewModel()
            )
        }
        composable(
            "addBooking/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AddBookingScreen(
                navController = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "editBooking/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            EditBookingScreen(
                navController = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "profileVisit/{token}/{hairdresserId}",
            arguments = listOf(
                navArgument("token") { defaultValue = "" },
                navArgument("hairdresserId") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ProfileVisitScreen(
                token = token,
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                onBookClick = { navController.navigate("booking/$token") },
                navController = { route -> navController.navigate(route) }
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
                viewModel = viewModel<BookingViewModel>()
            )
        }
        composable(
            "postDetail/{token}/{imageId}/{description}",
            arguments = listOf(
                navArgument("token") { defaultValue = "" },
                navArgument("imageId") { defaultValue = "0" },
                navArgument("description") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val imageId = backStackEntry.arguments?.getString("imageId")?.toIntOrNull() ?: 0
            val encodedDescription = backStackEntry.arguments?.getString("description") ?: ""
            val description = URLDecoder.decode(encodedDescription, "UTF-8")
            PostDetailScreen(
                token = token,
                imageId = imageId,
                description = description,
                onHomeClick = { navController.navigate("clientHome/$token") },
                onOrdersClick = { navController.navigate("orders/$token") },
                onProfileClick = { navController.navigate("clientProfile/$token") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "editProfile/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            EditProfile(
                token = token,
                onSaveChanges = { newName, newBio ->
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() },
                navController = { route -> navController.navigate(route) }
            )
        }
        composable(
            "addPost/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AddPostScreen(
                token = token,
                onBackClick = { navController.popBackStack() },
                navController = { route -> navController.navigate(route) }
            )
        }
        composable(
            "profile/{token}",
            arguments = listOf(navArgument("token") { defaultValue = "" })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            // Placeholder screen
        }
    }
}