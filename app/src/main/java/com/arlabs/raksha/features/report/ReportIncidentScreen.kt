package com.arlabs.raksha.features.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.ui.theme.RakshaTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIncidentScreen(
    // navHostController: NavHostController, // Add this for navigation
    viewModel: ReportIncidentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    // 1. Use GradientBox as the root composable to fill the entire screen
    GradientBox(modifier = Modifier.fillMaxSize()) {

        // --- Bottom Sheet for Incident Type Selection ---
        if (state.isIncidentTypeSheetVisible) {
            ModalBottomSheet(onDismissRequest = { viewModel.hideIncidentTypeSheet() }) {
                IncidentTypeSelectionSheet(
                    onTypeSelected = { viewModel.onIncidentTypeSelected(it) }
                )
            }
        }

        // 2. Make the Scaffold transparent so the gradient shows through
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { /* TODO: navHostController.popBackStack() */ }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White // Keep icon white
                            )
                        }
                    },
                    // 3. Make the TopAppBar transparent
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            // 4. Make the Scaffold's main container transparent
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Apply padding from the transparent Scaffold
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Title ---
                Text(
                    text = "Report the Incident. We'll Take It Forward.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                // --- Incident Type Selector ---
                Text(
                    text = "Incident Type",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReportInputRow(
                    icon = state.selectedIncidentType?.icon ?: Icons.Default.Warning,
                    text = state.selectedIncidentType?.title ?: "Select Incident Type",
                    onClick = { viewModel.showIncidentTypeSheet() }
                )

                // --- Location Selector ---
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Location",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReportInputRow(
                    icon = Icons.Default.LocationOn,
                    text = state.location,
                    onClick = { /* TODO: Open Map/Location Picker */ }
                )

                // --- Description Text Field ---
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Description",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = state.description,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("Please provide incident details") },
                    shape = RoundedCornerShape(12.dp),
                    // Set TextField colors for a light-on-dark theme
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        // Set text field background to a slightly transparent white
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White
                    )
                )

                // --- Severity Slider ---
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Severity Level: ${state.severity.toInt()}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Slider(
                    value = state.severity,
                    onValueChange = { viewModel.onSeverityChange(it) },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    )
                )

                // --- Submit Button ---
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.onSubmitReport() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = Color.White
                    ),
                    enabled = state.selectedIncidentType != null && !state.isSubmitting
                ) {
                    if (state.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Submit", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * A reusable composable for the white, clickable input rows.
 */
@Composable
private fun ReportInputRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            // Set row background to a slightly transparent white
            .background(Color.White.copy(alpha = 0.9f))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            // Set icon tint to a dark color to be visible on the light row
            tint = Color.DarkGray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = Color.Black, // Text on the row should be black
            fontSize = 16.sp
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Select",
            tint = Color.Gray
        )
    }
}

/**
 * The content for the ModalBottomSheet.
 */
@Composable
private fun IncidentTypeSelectionSheet(
    onTypeSelected: (IncidentType) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(
            text = "Select Incident Type",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            incidentTypes.groupBy { it.category }.forEach { (category, types) ->
                item {
                    Text(
                        text = category.name.lowercase().replaceFirstChar { it.titlecase(Locale.ROOT) },
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary, // Use theme color for headers
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(types) { incidentType ->
                    ListItem(
                        headlineContent = { Text(incidentType.title) },
                        leadingContent = {
                            Icon(
                                imageVector = incidentType.icon,
                                contentDescription = incidentType.title,
                                tint = MaterialTheme.colorScheme.primary // Tint icons
                            )
                        },
                        modifier = Modifier.clickable { onTypeSelected(incidentType) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportIncidentScreenPreview() {
    RakshaTheme {
        ReportIncidentScreen()
    }
}

