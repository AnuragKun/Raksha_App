package com.arlabs.raksha.features.onboarding.UserPreferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arlabs.raksha.domain.usecase.GetUserPreferencesUseCase
import com.arlabs.raksha.domain.usecase.SetUserPreferencesUseCase
import com.arlabs.raksha.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserPreferencesUseCase: SetUserPreferencesUseCase
) : ViewModel() {


    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {

        viewModelScope.launch {
            val isFirstTime = getUserPreferencesUseCase.isFirstTimeLogin().first()
            val isLoggedIn = getUserPreferencesUseCase.isLoggedIn().first()


            _startDestination.value = when {
                isFirstTime -> Routes.OnBoardingScreen
                !isLoggedIn -> Routes.AuthenticationScreen
                else -> Routes.MainScreen
            }
        }
    }

    
    fun onOnboardingComplete() {
        viewModelScope.launch {
            setUserPreferencesUseCase.setFirstTimeLogin(false)
        }
    }
}