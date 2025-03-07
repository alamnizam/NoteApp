package com.codeturtle.notes.common.utils

sealed class Resource<T>(
    val data: T? = null,
    val errorData: ErrorResponse? = null,
    val errorMessage: String? = null
) {
    class Loading<T>() : Resource<T>()
    class Success<T>(data: T?) : Resource<T>(data = data)
    class DataError<T>(errorData: ErrorResponse?) : Resource<T>(errorData = errorData)
    class Error<T>(error: String?) : Resource<T>(errorMessage = error)
}