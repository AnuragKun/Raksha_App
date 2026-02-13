package com.arlabs.raksha.features.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<String>>(emptyList())
    val searchResults: StateFlow<List<String>> = _searchResults.asStateFlow()

    private val _currentLocationResults = MutableStateFlow<List<String>>(emptyList())
    val currentLocationResults: StateFlow<List<String>> = _currentLocationResults.asStateFlow()

    private val _heatmapTileProvider = MutableStateFlow<com.google.maps.android.heatmaps.HeatmapTileProvider?>(null)
    val heatmapTileProvider: StateFlow<com.google.maps.android.heatmaps.HeatmapTileProvider?> = _heatmapTileProvider.asStateFlow()

    private var placesClient: PlacesClient? = null
    private var sessionToken: AutocompleteSessionToken? = null

    init {
        initializePlacesClient()
        loadHeatmapData()
    }

    private fun initializePlacesClient() {
        try {
            if (Places.isInitialized()) {
                placesClient = Places.createClient(context)
                sessionToken = AutocompleteSessionToken.newInstance()
                Log.d("HomeViewModel", "Places client initialized successfully")
            } else {
                Log.w("HomeViewModel", "Places SDK not initialized")
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Failed to initialize Places client", e)
        }
    }

    private fun loadHeatmapData() {
        try {
            val data = HeatmapData.getDummySafetyPoints()
            if (data.isNotEmpty()) {
                val provider = com.google.maps.android.heatmaps.HeatmapTileProvider.Builder()
                    .weightedData(data)
                    .radius(50)
                    .build()
                _heatmapTileProvider.value = provider
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Failed to load heatmap data", e)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.length >= 3) {
            searchPlaces(query, isDestination = true)
        } else {
            _searchResults.value = emptyList()
        }
    }

    fun onCurrentLocationQueryChanged(query: String) {
        if (query.length >= 3) {
            searchPlaces(query, isDestination = false)
        } else {
            _currentLocationResults.value = emptyList()
        }
    }

    private fun searchPlaces(query: String, isDestination: Boolean) {
        val client = placesClient ?: return
        val token = sessionToken ?: AutocompleteSessionToken.newInstance()

        viewModelScope.launch {
            try {
                val request = FindAutocompletePredictionsRequest.builder()
                    .setSessionToken(token)
                    .setQuery(query)
                    .setCountries("IN") // Restrict to India for relevance
                    .build()

                val response = client.findAutocompletePredictions(request).await()
                val predictions = response.autocompletePredictions.map { it.getFullText(null).toString() }
                
                if (isDestination) {
                    _searchResults.value = predictions
                } else {
                    _currentLocationResults.value = predictions
                }
                
                Log.d("HomeViewModel", "Found ${predictions.size} predictions for: $query")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Places autocomplete failed", e)
                if (isDestination) {
                    _searchResults.value = emptyList()
                } else {
                    _currentLocationResults.value = emptyList()
                }
            }
        }
    }
}
