package com.codeturtle.notes.common.validation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidateEmailTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Email is empty`() {
        val email = ""
        val result = ValidateEmail().execute(email)
        assert(!result.success)
    }

    @Test
    fun `getting correct error message when email is empty`()  {
        val email = ""
        val result = ValidateEmail().execute(email)
        val expectedErrorMessage = "The email can't be blank"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }

    @Test
    fun `email is valid`() {
        val email = "alamnizam1992@gmail.com"
        val result = ValidateEmail().execute(email)
        assert(result.success)
    }

    @Test
    fun `email is not valid`() {
        val email = "alamnizam1992gmail"
        val result = ValidateEmail().execute(email)
        assert(!result.success)
    }

    @Test
    fun `getting correct error message when email is not valid`() {
        val email = "alamnizam1992gmail"
        val result = ValidateEmail().execute(email)
        val expectedErrorMessage = "That's not a valid email"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }
}