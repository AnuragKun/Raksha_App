package com.arlabs.raksha.features.profile

import android.Manifest
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.domain.model.UserData
import java.util.*
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val userData by viewModel.userData.collectAsState()
    
    // Local state for editing to avoid constant database writes on every keystroke
    var name by remember(userData) { mutableStateOf(userData.name) }
    var email by remember(userData) { mutableStateOf(userData.email) }
    var phone by remember(userData) { mutableStateOf(userData.phone) }
    var dob by remember(userData) { mutableStateOf(userData.dob) }
    var bloodGroup by remember(userData) { mutableStateOf(userData.bloodGroup) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            dob = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    GradientBox(modifier = modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("My Profile", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Section
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                // Form Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        
                        // Full Name
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Phone Number (Read Only)
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { /* Read Only */ },
                            label = { Text("Phone Number") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            trailingIcon = {
                                if (userData.isVerified || phone.isNotEmpty()) {
                                   Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = Color.Green)
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        if (userData.isVerified || phone.isNotEmpty()) {
                             Text(
                                "Verified", 
                                color = Color.Green, 
                                fontSize = 12.sp, 
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                             )
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        // Date of Birth
                        OutlinedTextField(
                            value = if (dob != 0L) SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(dob)) else "",
                            onValueChange = { },
                            label = { Text("Date of Birth") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { datePickerDialog.show() },
                            readOnly = true,
                            enabled = false, // Disable typing, handled by clickable
                            trailingIcon = {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.clickable { datePickerDialog.show() })
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Black,
                                disabledBorderColor = Color.Gray,
                                disabledLabelColor = Color.Gray,
                                disabledTrailingIconColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Blood Group
                        OutlinedTextField(
                            value = bloodGroup,
                            onValueChange = { bloodGroup = it },
                            label = { Text("Blood Group") },
                            placeholder = { Text("e.g. O+, A-") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Update Button
                        Button(
                            onClick = {
                                viewModel.saveUserData(
                                    userData.copy(
                                        name = name,
                                        email = email,
                                        phone = phone,
                                        dob = dob,
                                        bloodGroup = bloodGroup
                                    )
                                )
                                navController.popBackStack() 
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                        ) {
                            Text("Update Details", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Timed Safety Check
                var timerValue by remember { mutableFloatStateOf(30f) }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Timed Safety Check", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${timerValue.toInt()}:00", 
                                color = Color.Black, 
                                fontSize = 32.sp, 
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Default Timer: ${timerValue.toInt()} minutes", color = Color.Gray, fontSize = 14.sp)
                        }
                        
                        Slider(
                            value = timerValue,
                            onValueChange = { timerValue = it },
                            valueRange = 5f..120f,
                            steps = 0,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFE91E63),
                                activeTrackColor = Color(0xFFE91E63),
                                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                            )
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            listOf(15, 30, 60, -1).forEach { min ->
                                val label = if (min == -1) "Custom" else if (min == 60) "1 hour" else "$min min"
                                val isSelected = if (min == -1) false else timerValue.toInt() == min
                                
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { if(min != -1) timerValue = min.toFloat() },
                                    label = { Text(label, color = if(isSelected) Color.White else Color.Black) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFFE91E63),
                                        containerColor = Color.Gray.copy(alpha = 0.1f),
                                        labelColor = Color.Black
                                    ),
                                    border = null
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Account Verification
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Account Verification", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if(userData.isVerified) Icons.Default.CheckCircle else Icons.Default.Person,
                                contentDescription = null,
                                tint = if(userData.isVerified) Color.Green else Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Status: ${if (userData.isVerified) "Verified" else "Not Verified"}",
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Unlock community features",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        if (!userData.isVerified) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { 
                                        navController.navigate("verify_account_screen") 
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                                ) {
                                    Text("Verify Account", color = Color.White)
                                }
                                
                                Button(
                                    onClick = { /* Dismiss/Cancel logic */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                                ) {
                                    Text("Cancel", color = Color.Gray)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Custom SOS Message
                var sosMessage by remember { mutableStateOf("I might be in trouble. My last known location is...") }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Custom SOS Message", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = sosMessage,
                            onValueChange = { sosMessage = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedBorderColor = Color(0xFFE91E63),
                                unfocusedBorderColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
                            }
                        )
                    }
                }
            }
        }
    }
}