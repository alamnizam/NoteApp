package com.codeturtle.notes.notes.notes_list.domain.usecase

import com.codeturtle.notes.MainCoroutineRule
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import com.codeturtle.notes.notes.notes_list.domain.repository.NoteListRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class NoteListUseCaseShould{
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var userCase: NoteListUseCase
    private val repository: NoteListRepository = mock()
    private val data = mock<Response<List<NoteListResponseItem>>>()

    @Before
    fun setUp() {
        userCase = NoteListUseCase(repository)
    }

    @Test
    fun returnSuccessWhenGotDataFromRepository() = runTest {
        whenever(repository.getNoteList()).thenReturn(data)
        var result: Resource<List<NoteListResponseItem>>? = null
        userCase().collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data.body(), result?.data)
    }

    @Test
    fun returnErrorDataWhenNotAbleToGetDataFromRepository() = runTest {
        whenever(repository.getNoteList()).thenReturn(data)
        var result: Resource<List<NoteListResponseItem>>? = null
        userCase().collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(data.errorBody(), result?.errorData)
    }

    @Test
    fun returnErrorMessageDuringException() = runTest {
        whenever(repository.getNoteList()).thenThrow(
            java.lang.RuntimeException("Something went wrong")
        )
        var result: Resource<List<NoteListResponseItem>>? = null
        userCase().collect{
            result = it
        }
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Something went wrong", result?.errorMessage)
    }
}