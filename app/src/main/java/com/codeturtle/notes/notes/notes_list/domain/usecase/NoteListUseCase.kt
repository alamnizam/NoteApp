package com.codeturtle.notes.notes.notes_list.domain.usecase

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import com.codeturtle.notes.notes.notes_list.domain.repository.NoteListRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoteListUseCase(
    private val repository: NoteListRepository
) {
    operator fun invoke(): Flow<Resource<List<NoteListResponseItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getNoteList()
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()))
            } else {
                val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                emit(Resource.DataError(errorData = errorResponse))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}
