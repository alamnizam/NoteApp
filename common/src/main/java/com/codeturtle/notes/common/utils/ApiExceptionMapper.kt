package com.codeturtle.notes.common.utils

import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ApiExceptionMapper {
    fun toUserMessage(exception: Exception): String? = when (exception) {
        is CancellationException -> throw exception
        is SocketTimeoutException -> "The request timed out. Please try again"
        is IOException -> "Unable to connect. Check your internet connection and try again"
        is HttpException -> toUserMessage(exception.code())
        is JsonParseException -> null
        else -> null
    }

    fun toUserMessage(statusCode: Int): String? = when (statusCode) {
        400 -> "Please check your information and try again"
        401 -> "Your session has expired. Please sign in again"
        403 -> "You do not have permission to perform this action"
        404 -> "The requested item could not be found"
        408 -> "The request timed out. Please try again"
        409 -> "The request conflicts with existing data"
        429 -> "Too many requests. Please try again later"
        in 500..599 -> "The server is temporarily unavailable"
        else -> null
    }
}
