package com.codeturtle.notes.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

inline fun <T, reified E> safeApiCall(
    crossinline request: suspend () -> Response<T>
): Flow<Resource<T, E>> = flow {
    emit(Resource.Loading)

    try {
        val response = request()
        if (response.isSuccessful) {
            emit(Resource.Success(response.body()))
        } else {
            val errorType = object : TypeToken<E>() {}.type
            val error = Gson().fromJson<E>(response.errorBody()?.string(), errorType)
            emit(Resource.DataError(error))
        }
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: SocketTimeoutException) {
        emit(Resource.Error("Request timed out"))
    } catch (exception: IOException) {
        emit(Resource.Error("Unable to connect to server"))
    } catch (exception: Exception) {
        emit(Resource.Error("Something went wrong"))
    }
}.flowOn(Dispatchers.IO)
