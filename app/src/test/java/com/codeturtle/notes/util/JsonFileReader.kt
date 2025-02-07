package com.codeturtle.notes.util

import java.io.InputStreamReader

object JsonFileReader {
    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}