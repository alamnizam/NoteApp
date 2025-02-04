package com.codeturtle.notes.common.utils

import okhttp3.Interceptor
import okhttp3.Response

class IdlingResourceInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        EspressoIdlingResource.increment() // Track network call
        return try {
            chain.proceed(chain.request())
        } finally {
            EspressoIdlingResource.decrement() // Release when done
        }
    }
}