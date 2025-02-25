package com.codeturtle.notes.notes.notes_list.data.repository

import com.codeturtle.notes.notes.notes_list.data.network.NoteListApiService
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponse
import com.codeturtle.notes.util.JsonFileReader
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteListRepositoryImplShould{
    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: NoteListRepositoryImpl
    private lateinit var apiService: NoteListApiService

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NoteListApiService::class.java)
        repository = NoteListRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun returnSuccessResponseFromApiService() = runTest {
        val dataString = JsonFileReader.getJsonContent("note_list_success.json")
        val data = Gson().fromJson(dataString, NoteListResponse::class.java)
        val mockResponse = MockResponse()
        mockResponse.setBody(dataString)
        mockWebServer.enqueue(mockResponse)
        val result = repository.getNoteList()
        assertEquals(data.size, result.body()?.size)
    }
}