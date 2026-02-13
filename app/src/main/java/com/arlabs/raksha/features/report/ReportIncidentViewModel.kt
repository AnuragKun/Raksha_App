package com.arlabs.raksha.features.report


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class to hold the entire state of the Report screen
data class ReportIncidentState(
    val selectedIncidentType: IncidentType? = null,
    val location: String = "Using Current Location", // Can be updated
    val description: String = "",
    val severity: Float = 5f, // Default severity
    val isIncidentTypeSheetVisible: Boolean = false,
    val isSubmitting: Boolean = false
)

@HiltViewModel
class ReportIncidentViewModel @Inject constructor(
    private val reportRepository: com.arlabs.raksha.domain.repository.ReportRepository,
    private val fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient
) : ViewModel() {

    private val _state = MutableStateFlow(ReportIncidentState())
    val state = _state.asStateFlow()

    init {
        fetchCurrentLocation()
    }

    private fun fetchCurrentLocation() {
        try {
            // Check permissions before calling (usually handled by UI/Activity, but we hope permissions are granted)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val locString = "${location.latitude}, ${location.longitude}"
                    _state.update { it.copy(location = locString) }
                }
            }
        } catch (e: SecurityException) {
            _state.update { it.copy(location = "Location Permission Denied") }
        } catch (e: Exception) {
             _state.update { it.copy(location = "Location Unavailable") }
        }
    }

    fun onDescriptionChange(text: String) {
        _state.update { currentState ->
            currentState.copy(description = text)
        }
    }

    fun onSeverityChange(severity: Float) {
        _state.update { currentState ->
            currentState.copy(severity = severity)
        }
    }

    fun showIncidentTypeSheet() {
        _state.update { currentState ->
            currentState.copy(isIncidentTypeSheetVisible = true)
        }
    }

    fun hideIncidentTypeSheet() {
        _state.update { currentState ->
            currentState.copy(isIncidentTypeSheetVisible = false)
        }
    }

    fun onIncidentTypeSelected(incidentType: IncidentType) {
        _state.update { currentState ->
            currentState.copy(selectedIncidentType = incidentType)
        }
        hideIncidentTypeSheet()
    }

    fun onSubmitReport() {
        val currentState = _state.value
        if (currentState.selectedIncidentType == null) return
        
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }
            
            val result = reportRepository.submitReport(
                type = currentState.selectedIncidentType.title,
                description = currentState.description,
                severity = currentState.severity,
                location = currentState.location,
                timestamp = System.currentTimeMillis()
            )
            
            
            _state.update { it.copy(isSubmitting = false) }
            
            when (result) {
                is com.arlabs.raksha.domain.util.Result.Success -> {
                    // Handle success
                    _state.update { it.copy(description = "", selectedIncidentType = null, severity = 5f) }
                }
                is com.arlabs.raksha.domain.util.Result.Failure -> {
                    // Handle error (maybe show a snackbar or update state)
                }
                else -> {}
            }
        }
    }
}

