package com.codeturtle.notes.common.utils

import android.util.Log

fun interface ErrorReporter {
    fun report(throwable: Throwable)
}

object LogcatErrorReporter : ErrorReporter {
    override fun report(throwable: Throwable) {
        Log.e(TAG, "API request failed", throwable)
    }

    private const val TAG = "ApiError"
}
