package com.arlabs.raksha.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val isFirstTimeLogin: Flow<Boolean>
    val isLoggedIn: Flow<Boolean>
    suspend fun setFirstTimeLogin(isFirstTime: Boolean)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
}