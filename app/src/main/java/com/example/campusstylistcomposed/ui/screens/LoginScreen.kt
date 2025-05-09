package com.example.campusstylistcomposed.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel // Import for hiltViewModel
import com.example.campusstylistcomposed.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: (String, Boolean, String) -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel() // Changed from viewModel() to hiltViewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val token by viewModel.token.collectAsState()
    val role by viewModel.role.collectAsState()
    val userId by viewModel.userId.collectAsState()

    var autoNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty() && !autoNavigated) {
            autoNavigated = true
            val isHairdresser = role?.uppercase() == "HAIRDRESSER"
            onLoginSuccess(token!!, isHairdresser ?: false, userId ?: token.hashCode().toString())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222020))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val width = size.width
            val height = size.height

            val wavePath = Path().apply {
                moveTo(0f, 0f)
                lineTo(width, 0f)
                lineTo(width, height * 0.7f)
                cubicTo(
                    width * 0.75f, height * 1.05f,
                    width * 0.25f, height * 0.85f,
                    0f, height * 0.95f
                )
                close()
            }

            drawPath(path = wavePath, color = Color(0xFFE0136C))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = "Welcome back",
                style = TextStyle(color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Login to CampusStylist",
                style = TextStyle(color = Color(0xFFA7A3A3), fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 24.dp).align(Alignment.Start)
            )

            Text(
                text = "Email",
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 8.dp).align(Alignment.Start)
            )

            TextField(
                value = email,
                onValueChange = { email = it; viewModel.updateEmail(it) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password",
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 8.dp).align(Alignment.Start)
            )

            TextField(
                value = password,
                onValueChange = { password = it; viewModel.updatePassword(it) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFFE0136C))
            } else {
                Button(
                    onClick = { viewModel.login(onLoginSuccess) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0136C)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Login", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red, fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = TextStyle(color = Color(0xFFA7A3A3), fontSize = 16.sp)
                )

                TextButton(onClick = onNavigateToSignUp) {
                    Text(
                        text = "SIGN UP",
                        style = TextStyle(color = Color(0xFFE0136C), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}