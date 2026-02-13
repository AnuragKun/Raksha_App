package com.arlabs.raksha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arlabs.raksha.domain.usecase.GetUserPreferencesUseCase
import com.arlabs.raksha.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            val isLoggedIn = getUserPreferencesUseCase.isLoggedIn().first()
            val isFirstTime = getUserPreferencesUseCase.isFirstTimeLogin().first()

            if (isLoggedIn) {
                _startDestination.value = Routes.MainScreen
            } else if (isFirstTime) {
                _startDestination.value = Routes.OnBoardingScreen
            } else {
                _startDestination.value = Routes.AuthenticationScreen
            }
        }
    }
}
