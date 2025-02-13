package com.codeturtle.notes.common.utils

import android.util.Log
import com.codeturtle.notes.common.preference.tokken.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        runBlocking {
            val token = tokenManager.getToken().toString()
            Log.d("Token", token)
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}