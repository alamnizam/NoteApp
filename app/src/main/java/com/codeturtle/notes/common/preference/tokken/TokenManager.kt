package com.codeturtle.notes.common.preference.tokken

import androidx.datastore.core.DataStore
import com.codeturtle.notes.common.preference.tokken.model.UserPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    suspend fun saveToken(token: String) {
        dataStore.updateData {
            it.copy(token = token)
        }
    }

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.updateData {
            it.copy(isLoggedIn = isLoggedIn)
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences.token
    }

    suspend fun getIsLoggedIn(): Boolean {
        val preferences = dataStore.data.first()
        return preferences.isLoggedIn
    }

    suspend fun clearData() {
        dataStore.updateData {
            it.copy(token = "", isLoggedIn = false)
        }
    }
}