package com.codeturtle.notes.common.utils

import com.codeturtle.notes.common.token.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = runBlocking(Dispatchers.IO) {
            tokenManager.getToken()
        }
        if (!token.isNullOrBlank()) {
            request.addHeader("Authorization", token)
        }
        return chain.proceed(request.build())
    }
}
