package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ValidateEmailTest {

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
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