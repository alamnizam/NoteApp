package com.codeturtle.notes.authentication.registration.domain.use_case

import com.codeturtle.notes.authentication.registration.data.model.RegisterRequest
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.authentication.registration.domain.repository.RegisterRepository
import com.codeturtle.notes.common.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    operator fun invoke(request: RegisterRequest) = flow<Resource<Response<RegisterResponse>>> {
        emit(Resource.Success(data = repository.register(request)))
    }.onStart{
        emit(Resource.Loading())
    }.catch {
        emit(Resource.Error(error = "Something went wrong"))
    }.flowOn(Dispatchers.IO)
}