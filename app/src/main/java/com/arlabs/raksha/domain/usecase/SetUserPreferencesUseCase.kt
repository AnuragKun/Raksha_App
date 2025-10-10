package com.arlabs.raksha.domain.usecase

import com.arlabs.raksha.domain.repository.UserPreferencesRepository

class SetUserPreferencesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun setFirstTimeLogin(isFirstTime: Boolean){
        userPreferencesRepository.setFirstTimeLogin(isFirstTime)
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean){
        userPreferencesRepository.setLoggedIn(isLoggedIn)
    }
}