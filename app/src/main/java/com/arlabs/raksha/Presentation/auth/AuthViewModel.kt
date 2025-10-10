package com.arlabs.raksha.Presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arlabs.raksha.domain.usecase.GoogleSignInUseCase
import com.arlabs.raksha.domain.usecase.SetUserPreferencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import com.arlabs.raksha.domain.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val setUserPreferencesUseCase: SetUserPreferencesUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState: StateFlow<Result<String>> = _authState.asStateFlow()

    fun signInWithGoogle(account: GoogleSignInAccount){
        Log.d("AuthViewModel", "Starting Google sign in for: ${account.email}")
        _authState.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = googleSignInUseCase(account)
                Log.d("AuthViewModel", "Google sign in result: $result")
                _authState.value = result
                if (result is Result.Success) {
                    setUserPreferencesUseCase.setLoggedIn(true)
                    setUserPreferencesUseCase.setFirstTimeLogin(false)
                } else if (result is Result.Failure) {
                    Log.e("AuthViewModel", "Google sign in failed: ${result.message}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel","Exception in Googlesign in: ${e.message}",e)
                _authState.value = Result.Failure(e.message ?: "Google sign in failed")
            }
        }
    }

    fun resetAuthState(){
        _authState.value = Result.Idle
    }
}