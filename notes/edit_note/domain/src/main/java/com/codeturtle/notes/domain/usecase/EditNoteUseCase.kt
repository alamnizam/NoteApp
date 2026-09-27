package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.ErrorResponse
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.EditNoteRequest
import com.codeturtle.notes.domain.model.EditNoteResponse
import com.codeturtle.notes.domain.repository.EditNoteRepository
import kotlinx.coroutines.flow.Flow

class EditNoteUseCase(
    private val repository: EditNoteRepository
) {
    operator fun invoke(request: EditNoteRequest): Flow<Resource<EditNoteResponse, ErrorResponse>> =
        safeApiCall { repository.editNote(request) }
}