package com.arlabs.raksha.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arlabs.raksha.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")
        private val IS_FIRST_TIME_LOGIN = booleanPreferencesKey("is_first_time_login")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        
        // Profile Keys
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_DOB = longPreferencesKey("user_dob")
        private val USER_BLOOD_GROUP = stringPreferencesKey("user_blood_group")
        private val USER_IS_VERIFIED = booleanPreferencesKey("user_is_verified")
    }

    val isFirstTimeLogin: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_TIME_LOGIN] ?: true
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val userData: Flow<UserData> = context.dataStore.data.map { preferences ->
        UserData(
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            phone = preferences[USER_PHONE] ?: "",
            dob = preferences[USER_DOB] ?: 0L,
            bloodGroup = preferences[USER_BLOOD_GROUP] ?: "",
            isVerified = preferences[USER_IS_VERIFIED] ?: false
        )
    }

    suspend fun setFirstTimeLogin(isFirstTime: Boolean){
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME_LOGIN] = isFirstTime
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean){
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userData.name
            preferences[USER_EMAIL] = userData.email
            preferences[USER_PHONE] = userData.phone
            preferences[USER_DOB] = userData.dob
            preferences[USER_BLOOD_GROUP] = userData.bloodGroup
            preferences[USER_IS_VERIFIED] = userData.isVerified
        }
    }
}