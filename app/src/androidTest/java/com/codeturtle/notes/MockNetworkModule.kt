package com.codeturtle.notes


import com.codeturtle.notes.common.di.NetworkModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule:NetworkModule() {
    override fun baseUrl(): String {
        return "http://localhost:8080/"
    }
}