package com.arlabs.raksha.features.safezone

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SafePlace(
    val name: String,
    val type: String, // Hospital, Police, etc.
    val address: String,
    val distance: String
)

@HiltViewModel
class SafeZoneViewModel @Inject constructor() : ViewModel() {

    private val _safePlaces = MutableStateFlow<List<SafePlace>>(emptyList())
    val safePlaces = _safePlaces.asStateFlow()

    init {
        loadDummySafePlaces()
    }

    private fun loadDummySafePlaces() {
        _safePlaces.value = listOf(
            SafePlace("City Hospital", "Hospital", "123 Main St", "0.5 km"),
            SafePlace("Central Police Station", "Police", "456 Safety Blvd", "1.2 km"),
            SafePlace("Women's Shelter", "Shelter", "789 Care Ln", "2.0 km"),
            SafePlace("Community Clinic", "Hospital", "321 Health Rd", "2.5 km")
        )
    }
}
