package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.AddNoteRequest
import com.codeturtle.notes.domain.model.AddNoteResponse
import com.codeturtle.notes.domain.repository.AddNoteRepository
import kotlinx.coroutines.flow.Flow

class AddNoteUseCase(
    private val repository: AddNoteRepository
) {
    operator fun invoke(request: AddNoteRequest): Flow<Resource<AddNoteResponse>> =
        safeApiCall { repository.addNote(request) }
}
