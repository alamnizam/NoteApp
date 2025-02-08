package com.codeturtle.notes.common.validation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidateUsernameTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Username is empty`() {
        val username = ""
        val result = ValidateUsername().execute(username)
        assertEquals(result.success,false)
    }

    @Test
    fun `Username is not empty`() {
        val username = "Nizam Alam"
        val result = ValidateUsername().execute(username)
        assertEquals(result.success,true)
    }

    @Test
    fun `getting correct error message when username is empty`(){
        val username = ""
        val result = ValidateUsername().execute(username)
        val expectedErrorMessage = "The username can't be blank"
        assertEquals(result.errorMessage?.asString(context)?.equals(expectedErrorMessage),true)
    }
}