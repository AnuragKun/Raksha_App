package com.arlabs.raksha.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.Common.TopCurveShape
import com.arlabs.raksha.domain.util.Result
import com.arlabs.raksha.navigation.Routes

@Composable
fun PhoneNumberScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val authState by viewModel.authState.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is Result.Success && (authState as Result.Success).data == "Code Sent") {
            navController.navigate(Routes.OtpScreen)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Background
        GradientBox(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Verify Phone",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Enter your phone number to continue",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Phone Input Card
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
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Phone Number", color = Color.White) },
                            placeholder = { Text("+91 9999999999", color = Color.White.copy(alpha = 0.5f)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { 
                                if(phoneNumber.isNotEmpty()){
                                     // Ensure country code exists or append default
                                     val formattedNumber = if(phoneNumber.startsWith("+")) phoneNumber else "+91$phoneNumber"
                                     viewModel.sendOtp(formattedNumber, context as android.app.Activity)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF6C63FF) // Use a primary color here or black
                            )
                        ) {
                            if (authState is Result.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color(0xFF6C63FF)
                                )
                            } else {
                                Text("Send OTP", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                
                if (authState is Result.Failure) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = (authState as Result.Failure).message,
                        color = Color.Red,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}
