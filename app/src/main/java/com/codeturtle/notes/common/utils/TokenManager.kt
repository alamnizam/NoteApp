package com.codeturtle.notes.common.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codeturtle.notes.common.constant.Pref.USER_TOKEN
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(USER_TOKEN)] = token
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[stringPreferencesKey(USER_TOKEN)]
    }
}