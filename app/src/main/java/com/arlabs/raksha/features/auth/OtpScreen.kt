package com.arlabs.raksha.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.domain.util.Result
import com.arlabs.raksha.navigation.Routes

@Composable
fun OtpScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val authState by viewModel.authState.collectAsState()
    var otpCode by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState is Result.Success && (authState as Result.Success).data == "Login Complete") {
             navController.navigate(Routes.MainScreen) {
                 popUpTo(Routes.AuthenticationScreen) { inclusive = true }
             }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        GradientBox(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Verify OTP",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Enter the code sent to your phone",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = otpCode,
                            onValueChange = { if(it.length <= 6) otpCode = it },
                            label = { Text("OTP Code", color = Color.White) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, letterSpacing = 8.sp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { 
                                if (otpCode.length == 6) {
                                    viewModel.verifyOtp(otpCode)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF6C63FF)
                            )
                        ) {
                            if (authState is Result.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color(0xFF6C63FF)
                                )
                            } else {
                                Text("Verify", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
