package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.domain.model.DeleteNoteResponse
import com.codeturtle.notes.domain.repository.DeleteNoteRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteNoteUseCase(
    private val repository: DeleteNoteRepository
) {
    operator fun invoke(id:Int): Flow<Resource<DeleteNoteResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.deleteNote(id)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()))
            } else {
                val errorResponse =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                emit(Resource.DataError(errorData = errorResponse))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}