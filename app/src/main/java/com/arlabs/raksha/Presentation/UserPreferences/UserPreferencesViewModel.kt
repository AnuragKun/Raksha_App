package com.arlabs.raksha.Presentation.UserPreferences

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arlabs.raksha.domain.model.UserPreferencesState
import com.arlabs.raksha.domain.usecase.GetUserPreferencesUseCase
import com.arlabs.raksha.domain.usecase.SetUserPreferencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class UserPreferencesViewModel(
    private val getUserPreferenceUseCase: GetUserPreferencesUseCase,
    private val setUserPreferenceUseCase: SetUserPreferencesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserPreferencesState())
    val state : StateFlow<UserPreferencesState> = _state.asStateFlow()

    init {
        observeUserPreferences()
    }

    private fun observeUserPreferences() {
        viewModelScope.launch {
            combine(
                getUserPreferenceUseCase.isFirstTimeLogin(), //false
                getUserPreferenceUseCase.isLoggedIn()       //true
            ){isFirstTime, isLoggedIn ->
                Log.d("UserPreferncesViewModel","DataStore values changed - isFirstTime: $isFirstTime,isLoggedIn: $isLoggedIn")
                UserPreferencesState(
                    isFirstTimeLogin = isFirstTime,
                    isLoggedIn = isLoggedIn,
                    isLoading = false
                )
            }.collect { newState ->
                Log.d("UserPreferencesViewModel","Updating state - isFirstTime: ${newState.isFirstTimeLogin}, isLoggedIn: ${newState.isLoggedIn}")
                _state.value = newState
            }
        }
    }
}