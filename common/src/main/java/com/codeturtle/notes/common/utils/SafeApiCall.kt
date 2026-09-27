   package com.codeturtle.notes.common.utils

   import com.google.gson.Gson
   import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <T, reified E> safeApiCall(
    errorReporter: ErrorReporter = LogcatErrorReporter,
    crossinline exceptionMessage: (Exception) -> String? = ApiExceptionMapper::toUserMessage,
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
    } catch (exception: Exception) {
        errorReporter.report(exception)
        emit(Resource.Error(exceptionMessage(exception)))
    }
}.flowOn(Dispatchers.IO)
