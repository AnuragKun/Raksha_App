package com.arlabs.raksha.features.report

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.ui.graphics.vector.ImageVector

// A data class to hold info for each incident type
data class IncidentType(
    val title: String,
    val icon: ImageVector,
    val category: IncidentCategory
)

// Enum to group the incident types
enum class IncidentCategory {
    HARASSMENT, ENVIRONMENT, ACTIVITY, OTHER
}

// The master list of all incident types you can report
val incidentTypes = listOf(
    IncidentType("Verbal Harassment", Icons.Default.VolumeUp, IncidentCategory.HARASSMENT),
    IncidentType("Being Followed / Stalked", Icons.AutoMirrored.Filled.DirectionsRun, IncidentCategory.HARASSMENT),
    IncidentType("Indecent Exposure", Icons.Default.Report, IncidentCategory.HARASSMENT),
    IncidentType("Poor Lighting", Icons.Default.Lightbulb, IncidentCategory.ENVIRONMENT),
    IncidentType("Isolated / Deserted Area", Icons.Default.People, IncidentCategory.ENVIRONMENT),
    IncidentType("Suspicious Person", Icons.Default.Security, IncidentCategory.ACTIVITY),
    IncidentType("Assault / Robbery", Icons.Default.FlashOn, IncidentCategory.ACTIVITY),
    IncidentType("Other", Icons.Default.QuestionMark, IncidentCategory.OTHER)
)
