package com.codeturtle.notes.common.utils

import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

private const val GENERIC_ERROR_MESSAGE = "Something went wrong"
private val gson = Gson()

fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val errorResponse = response.errorBody()?.string()?.let { errorBody ->
                runCatching { gson.fromJson(errorBody, ErrorResponse::class.java) }.getOrNull()
            }
            emit(Resource.DataError(errorData = errorResponse))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        emit(Resource.Error(GENERIC_ERROR_MESSAGE))
    }
}.flowOn(Dispatchers.IO)
