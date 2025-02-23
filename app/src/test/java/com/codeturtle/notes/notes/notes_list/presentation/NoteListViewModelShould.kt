package com.codeturtle.notes.notes.notes_list.presentation

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import com.codeturtle.notes.notes.notes_list.domain.usecase.NoteListUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class NoteListViewModelShould {

    private lateinit var viewModel: NoteListViewModel

    private val useCase: NoteListUseCase = mock()
    private val data: List<NoteListResponseItem> = mock()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = NoteListViewModel(useCase)
        viewModel.getNoteList()
    }

    @Test
    fun validateSuccessStateDataIsStoredOnNoteList() = runTest {
        whenever(useCase()).thenReturn(
            flow{
                emit(Resource.Success(data = data))
            }
        )
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data, viewModel.noteListResponse.value.data)
    }

    @Test
    fun validateErrorStateDataIsStoredOnNoteList() = runTest {
        whenever(useCase()).thenReturn(
            flow{
                emit(Resource.Error(error = "Something went wrong"))
            }
        )
        viewModel.onEvent(NoteListUIEvent.AddNoteClicked)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", viewModel.noteListResponse.value.errorMessage)
    }
}