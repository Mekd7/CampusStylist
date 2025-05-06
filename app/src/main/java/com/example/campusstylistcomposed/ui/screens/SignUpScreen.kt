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
import com.example.campusstylistcomposed.data.AuthRequest
import com.example.campusstylistcomposed.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    onSignupSuccess: (String, Boolean) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val pinkColor = Color(0xFFE0136C)
    val darkColor = Color(0xFF222020)
    val grayColor = Color(0xFFA7A3A3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColor)
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

            drawPath(
                path = wavePath,
                color = pinkColor
            )
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
                text = "Welcome!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create an account to join CampusStylist",
                style = TextStyle(
                    color = grayColor,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Email",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
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
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Select Role",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { selectedRole = "STUDENT" },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == "STUDENT") pinkColor else grayColor
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Client",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Button(
                    onClick = { selectedRole = "HAIRDRESSER" },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == "HAIRDRESSER") pinkColor else grayColor
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Hairdresser",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(color = pinkColor)
            } else {
                Button(
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.authApiService.signup(
                                    AuthRequest(username, password, selectedRole!!)
                                )
                                val isHairdresser = selectedRole == "HAIRDRESSER"
                                onSignupSuccess(response.token, isHairdresser)
                            } catch (e: Exception) {
                                errorMessage = "Signup failed: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    enabled = selectedRole != null && username.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkColor,
                        disabledContainerColor = grayColor
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    style = TextStyle(
                        color = grayColor,
                        fontSize = 16.sp
                    )
                )

                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = "SIGN IN",
                        style = TextStyle(
                            color = pinkColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}