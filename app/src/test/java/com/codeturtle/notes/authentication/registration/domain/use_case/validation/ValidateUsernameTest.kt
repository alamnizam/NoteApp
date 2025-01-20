package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ValidateUsernameTest {

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
    }

    @Test
    fun `Username is empty`() {
        val username = ""
        val result = ValidateUsername().execute(username)
        assert(!result.success)
    }

    @Test
    fun `Username is not empty`() {
        val username = "Nizam Alam"
        val result = ValidateUsername().execute(username)
        assert(result.success)
    }

    @Test
    fun `Correct error message when username is empty`(){
        val username = ""
        val result = ValidateUsername().execute(username)
        val expectedErrorMessage = "The username can't be blank"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }
}