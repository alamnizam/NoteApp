package com.codeturtle.notes.notes.notes_list.mockwebserver

import android.content.ContentValues.TAG
import android.util.Log
import com.codeturtle.notes.common.constant.ServerUrlList.GET_NOTES
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class NoteListMockServerDispatcher {
    fun successDispatcher(map: Map<String, String>, token: String? = null): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.e(TAG, "dispatch: path = ${request.path}")
                val authHeader = request.getHeader("Authorization")
                if (token != null && authHeader != "Bearer $token") {
                    return MockResponse().setResponseCode(401).setBody("Unauthorized")
                }
                return when (request.path) {
                    "/$GET_NOTES" -> {
                        var json = ""
                        if (map.containsKey("/$GET_NOTES")) {
                            json = map["/$GET_NOTES"]!!
                        }
                        MockResponse()
                            .setResponseCode(200)
                            .addHeader("Authorization", "Bearer $token")
                            .setBody(getJsonContent(json))
                    }

                    else -> MockResponse().setResponseCode(200).setBody("")
                }
            }
        }
    }

    fun emptyNoteListDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/$GET_NOTES" -> {
                        var json = ""
                        if (map.containsKey("/$GET_NOTES")) {
                            json = map["/$GET_NOTES"]!!
                        }
                        MockResponse().setResponseCode(200).setBody(getJsonContent(json))
                    }
                    else -> MockResponse().setResponseCode(400).setBody("")
                }
            }
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}