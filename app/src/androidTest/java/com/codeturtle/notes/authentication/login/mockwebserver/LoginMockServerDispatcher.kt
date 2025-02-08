package com.codeturtle.notes.authentication.login.mockwebserver

import android.content.ContentValues.TAG
import android.util.Log
import com.codeturtle.notes.common.constant.ServerUrlList.LOGIN
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class LoginMockServerDispatcher {
    fun successDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.e(TAG, "dispatch: path = ${request.path}")
                return when (request.path) {
                    "/$LOGIN" -> {
                        var json = ""
                        if (map.containsKey("/$LOGIN")) {
                            json = map["/$LOGIN"]!!
                        }
                        MockResponse().setResponseCode(200).setBody(getJsonContent(json))
                    }

                    else -> MockResponse().setResponseCode(200).setBody("")
                }
            }
        }
    }

    fun emailPasswordErrorDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/$LOGIN" -> {
                        var json = ""
                        if (map.containsKey("/$LOGIN")) {
                            json = map["/$LOGIN"]!!
                        }
                        MockResponse().setResponseCode(400).setBody(getJsonContent(json))
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