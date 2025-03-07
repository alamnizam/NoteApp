package com.codeturtle.notes.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.codeturtle.notes.common.constant.Pref.DATA_STORE_FILE_NAME
import com.codeturtle.notes.common.tokken.TokenManager
import com.codeturtle.notes.common.tokken.model.UserPreferences
import com.codeturtle.notes.common.tokken.model.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSoreModule {
    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Provides
    @Singleton
    fun provideTokenManager(dataStore: DataStore<UserPreferences>): TokenManager {
        return TokenManager(dataStore)
    }
}