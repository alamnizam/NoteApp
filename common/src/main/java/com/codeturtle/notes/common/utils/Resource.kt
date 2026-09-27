package com.codeturtle.notes.common.utils

sealed class Resource<out T, out E>(
    val data: T? = null,
    val errorData: E? = null,
    val errorMessage: String? = null
) {
    data object Loading : Resource<Nothing, Nothing>()
    class Success<T>(data: T?) : Resource<T, Nothing>(data = data)
    class DataError<E>(errorData: E?) : Resource<Nothing, E>(errorData = errorData)
    class Error(error: String?) : Resource<Nothing, Nothing>(errorMessage = error)
}