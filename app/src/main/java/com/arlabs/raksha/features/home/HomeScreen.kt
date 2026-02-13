package com.arlabs.raksha.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.NavController
import com.arlabs.raksha.navigation.Routes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    var userName by remember { mutableStateOf("User") }
    var currentLocationQuery by remember { mutableStateOf("") }

    val destinationQuery by homeViewModel.searchQuery.collectAsState()
    val searchResults by homeViewModel.searchResults.collectAsState()
    val currentLocationResults by homeViewModel.currentLocationResults.collectAsState()


    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val defaultLocation = LatLng(28.6139, 77.2090)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                // Suppress missing permission check as we checked it in the boolean
                @SuppressLint("MissingPermission")
                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 15f)
                        homeViewModel.onCurrentLocationQueryChanged("${location.latitude},${location.longitude}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val mapUiSettings by remember(hasLocationPermission) {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = hasLocationPermission
            )
        )
    }

    var isSafetyModeEnabled by remember { mutableStateOf(false) }
    var showCurrentLocationSuggestions by remember { mutableStateOf(false) }
    var showDestinationSuggestions by remember { mutableStateOf(false) }

    GradientBox(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                androidx.compose.material3.FloatingActionButton(
                    onClick = { /* TODO: Trigger Emergency SOS */ },
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Emergency SOS")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.clickable { navController.navigate(Routes.ProfileScreen) }
                    ) {
                        Text(
                            "Hello!",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = userName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    // Safety Mode Toggle
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isSafetyModeEnabled) "Shield ON" else "Shield OFF",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSafetyModeEnabled) Color.Green else Color.White.copy(alpha = 0.7f)
                        )
                        androidx.compose.material3.Switch(
                            checked = isSafetyModeEnabled,
                            onCheckedChange = { isSafetyModeEnabled = it },
                            colors = androidx.compose.material3.SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.Green,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Find Your Safe Path",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Current Location Input
                Box(modifier = Modifier.fillMaxWidth().zIndex(2f)) {
                    Column {
                        OutlinedTextField(
                            value = currentLocationQuery,
                            onValueChange = {
                                currentLocationQuery = it
                                homeViewModel.onCurrentLocationQueryChanged(it)
                                showCurrentLocationSuggestions = it.isNotEmpty() && currentLocationQuery.length >= 3
                            },
                            label = { Text("Current Location", color = Color.White.copy(alpha = 0.7f)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.MyLocation,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        
                        // Current Location Suggestions Overlay
                         androidx.compose.animation.AnimatedVisibility(
                            visible = showCurrentLocationSuggestions && currentLocationResults.isNotEmpty()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Column {
                                    currentLocationResults.take(4).forEach { suggestion ->
                                        Text(
                                            text = suggestion,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    currentLocationQuery = suggestion
                                                    showCurrentLocationSuggestions = false
                                                }
                                                .padding(12.dp),
                                            color = Color.Black
                                        )
                                        androidx.compose.material3.HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Destination Input
                Box(modifier = Modifier.fillMaxWidth().zIndex(1f)) {
                    Column {
                        OutlinedTextField(
                            value = destinationQuery,
                            onValueChange = {
                                homeViewModel.onSearchQueryChanged(it)
                                showDestinationSuggestions = it.isNotEmpty() && destinationQuery.length >= 3
                            },
                            label = { Text("Destination", color = Color.White.copy(alpha = 0.7f)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AddLocation,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Destination Suggestions Overlay
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showDestinationSuggestions && searchResults.isNotEmpty()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Column {
                                    searchResults.take(4).forEach { suggestion ->
                                        Text(
                                            text = suggestion,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    homeViewModel.onSearchQueryChanged(suggestion)
                                                    showDestinationSuggestions = false
                                                }
                                                .padding(12.dp),
                                            color = Color.Black
                                        )
                                        androidx.compose.material3.HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Map Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = com.google.maps.android.compose.MapProperties(
                            isMyLocationEnabled = hasLocationPermission
                        ),
                        uiSettings = mapUiSettings
                    ) {
                        val tileProvider by homeViewModel.heatmapTileProvider.collectAsState()
                        tileProvider?.let { provider ->
                            com.google.maps.android.compose.TileOverlay(tileProvider = provider)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Start Navigation */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Start Navigation", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Emergency Contacts Section
                // Define a simple data class for internal use
                data class EmergencyContact(val name: String, val number: String)

                var emergencyContacts by remember { mutableStateOf(listOf<EmergencyContact>()) }

                val contactPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickContact()
                ) { uri: android.net.Uri? ->
                    uri?.let {
                        try {
                            val projection = arrayOf(
                                android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            context.contentResolver.query(it, projection, null, null, null)?.use { cursor ->
                                if (cursor.moveToFirst()) {
                                    val nameIndex = cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                                    val numberIndex = cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)
                                    
                                    // Check if indices are valid
                                    if (nameIndex != -1 && numberIndex != -1) {
                                        val name = cursor.getString(nameIndex) ?: "Unknown"
                                        val number = cursor.getString(numberIndex) ?: ""
                                        
                                        if (number.isNotEmpty()) {
                                            // Add to list if not already full and not duplicate
                                            if (emergencyContacts.size < 5 && !emergencyContacts.any { c -> c.number == number }) {
                                                emergencyContacts = emergencyContacts + EmergencyContact(name, number)
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            // Could show a toast here in a real app
                        }
                    }
                }

                val requestContactPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        contactPickerLauncher.launch(null)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Emergency Contacts (${emergencyContacts.size}/5)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // List of contacts
                        emergencyContacts.forEach { contact ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = contact.name, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                                    Text(text = contact.number, fontSize = 12.sp, color = Color.Gray)
                                }
                                
                                Row {
                                    // Call Button
                                    Button(
                                        onClick = {
                                            try {
                                                val intent = android.content.Intent(android.content.Intent.ACTION_DIAL).apply {
                                                    data = android.net.Uri.parse("tel:${contact.number}")
                                                }
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                        modifier = Modifier.padding(end = 8.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Call", fontSize = 12.sp)
                                    }
                                    
                                    // Delete Button (Optional, simple X)
                                    OutlinedButton(
                                        onClick = {
                                            emergencyContacts = emergencyContacts.filter { it.number != contact.number }
                                        },
                                        modifier = Modifier.size(width = 40.dp, height = 36.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("X", color = Color.Red, fontSize = 12.sp)
                                    }
                                }
                            }
                            androidx.compose.material3.HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (emergencyContacts.size < 5) {
                            OutlinedButton(
                                onClick = {
                                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                                        contactPickerLauncher.launch(null)
                                    } else {
                                        requestContactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Add Contact")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Share Location */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sharelocation),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Share Location", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}