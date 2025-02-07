package com.codeturtle.notes.authentication.registration.data.mockserver

import android.content.ContentValues.TAG
import android.util.Log
import com.codeturtle.notes.common.constant.ServerUrlList.REGISTER
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {
    fun successDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.e(TAG, "dispatch: path = ${request.path}")
                return when (request.path) {
                    "/$REGISTER" -> {
                        var json = ""
                        if (map.containsKey("/$REGISTER")) {
                            json = map["/$REGISTER"]!!
                        }
                        MockResponse().setResponseCode(200).setBody(getJsonContent(json))
                    }

                    else -> MockResponse().setResponseCode(200).setBody("")
                }
            }
        }
    }

    fun emailErrorDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/$REGISTER" -> {
                        var json = ""
                        if (map.containsKey("/$REGISTER")) {
                            json = map["/$REGISTER"]!!
                        }
                        MockResponse().setResponseCode(409).setBody(getJsonContent(json))
                    }
                    else -> MockResponse().setResponseCode(404).setBody("")
                }
            }
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}