package com.codeturtle.notes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.codeturtle.notes.common.constant.Pref
import com.codeturtle.notes.common.di.DataSoreModule
import com.codeturtle.notes.common.preference.tokken.TokenManager
import com.codeturtle.notes.common.preference.tokken.model.UserPreferences
import com.codeturtle.notes.common.preference.tokken.model.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSoreModule::class]
)
object TestDataStoreModule {

    private var instance: DataStore<UserPreferences>? = null

    @Singleton
    @Provides
    fun provideTestProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserPreferences> {
        return instance ?: synchronized(this) {
            instance ?: DataStoreFactory.create(
                serializer = UserPreferencesSerializer,
                produceFile = { appContext.dataStoreFile(Pref.TEST_DATA_STORE_FILE_NAME) },
                corruptionHandler = null,
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            ).also { instance = it }
        }
    }


    @Provides
    @Singleton
    fun provideTestTokenManager(dataStore: DataStore<UserPreferences>): TokenManager {
        return TokenManager(dataStore)
    }
}