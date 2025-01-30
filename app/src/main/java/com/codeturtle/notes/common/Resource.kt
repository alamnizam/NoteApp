package com.codeturtle.notes.common

import retrofit2.Response

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Loading<T>() : Resource<Response<T>>()
    class Success<T>(data: Response<T>?) : Resource<Response<T>>(data = data)
    class Error<T>(error: String?) :
        Resource<Response<T>>(error = error)
}