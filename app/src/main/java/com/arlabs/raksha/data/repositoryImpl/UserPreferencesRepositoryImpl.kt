package com.arlabs.raksha.data.repositoryImpl
import com.arlabs.raksha.data.local.UserPreferencesDataStore
import com.arlabs.raksha.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import com.arlabs.raksha.domain.model.UserData

class UserPreferencesRepositoryImpl(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserPreferencesRepository {
    override val isFirstTimeLogin: Flow<Boolean> = userPreferencesDataStore.isFirstTimeLogin

    override val isLoggedIn: Flow<Boolean> = userPreferencesDataStore.isLoggedIn

    override val userData: Flow<com.arlabs.raksha.domain.model.UserData> = userPreferencesDataStore.userData

    override suspend fun setFirstTimeLogin(isFirstTime: Boolean) {
        userPreferencesDataStore.setFirstTimeLogin(isFirstTime)
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        userPreferencesDataStore.setLoggedIn(isLoggedIn)
    }

    override suspend fun saveUserData(userData: com.arlabs.raksha.domain.model.UserData) {
        userPreferencesDataStore.saveUserData(userData)
    }
}
