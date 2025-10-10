package com.arlabs.raksha.domain.usecase

import com.arlabs.raksha.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUseCase (
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun isFirstTimeLogin(): Flow<Boolean> = userPreferencesRepository.isFirstTimeLogin

    fun isLoggedIn(): Flow<Boolean> = userPreferencesRepository.isLoggedIn
}