package com.codeturtle.notes.domain.usecase

import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.utils.safeApiCall
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.domain.repository.NoteListRepository
import kotlinx.coroutines.flow.Flow

class NoteListUseCase(
    private val repository: NoteListRepository
) {
    operator fun invoke(): Flow<Resource<List<NoteListResponseItem>>> =
        safeApiCall { repository.getNoteList() }
}
