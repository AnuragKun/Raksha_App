package com.arlabs.raksha.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")
        private val IS_FIRST_TIME_LOGIN = booleanPreferencesKey("is_first_time_login")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isFirstTimeLogin: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_TIME_LOGIN]?: true
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
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
}