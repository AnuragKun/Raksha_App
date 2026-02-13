package com.arlabs.raksha.domain.repository

import com.arlabs.raksha.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val isFirstTimeLogin: Flow<Boolean>
    val isLoggedIn: Flow<Boolean>
    val userData: Flow<UserData>
    suspend fun setFirstTimeLogin(isFirstTime: Boolean)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
    suspend fun saveUserData(userData: UserData)
}