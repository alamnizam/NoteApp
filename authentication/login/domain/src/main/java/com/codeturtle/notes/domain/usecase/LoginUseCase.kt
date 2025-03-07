package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.domain.model.LoginRequest
import com.codeturtle.notes.domain.model.LoginResponse
import com.codeturtle.notes.domain.repository.LoginRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(request: LoginRequest):Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.login(request)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()))
            } else {
                val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                emit(Resource.DataError(errorData = errorResponse))
            }
        }catch (e:Exception){
            emit(Resource.Error("Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}