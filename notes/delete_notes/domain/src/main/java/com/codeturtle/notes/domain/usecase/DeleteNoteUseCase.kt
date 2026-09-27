package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.DeleteNoteResponse
import com.codeturtle.notes.domain.repository.DeleteNoteRepository
import kotlinx.coroutines.flow.Flow

class DeleteNoteUseCase(
    private val repository: DeleteNoteRepository
) {
    operator fun invoke(id: Int): Flow<Resource<DeleteNoteResponse>> =
        safeApiCall { repository.deleteNote(id) }
}
