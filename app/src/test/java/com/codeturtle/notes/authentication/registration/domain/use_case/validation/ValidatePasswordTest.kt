package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ValidatePasswordTest {

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
    }

    @Test
    fun `Password is empty`() {
        val password = ""
        val result = ValidatePassword().execute(password)
        assert(!result.success)
    }

    @Test
    fun `getting correct error message when password is empty`(){
        val password = ""
        val result = ValidatePassword().execute(password)
        val expectedErrorMessage = "The password can't be blank"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }

    @Test
    fun `Password is not empty`() {
        val password = "password"
        val result = ValidatePassword().execute(password)
        assert(result.success)
    }

    @Test
    fun `Password is less than have 8 characters`() {
        val password = "pass"
        val result = ValidatePassword().execute(password)
        assert(!result.success)
    }

    @Test
    fun `Password have 8 characters`() {
        val password = "password"
        val result = ValidatePassword().execute(password)
        assert(result.success)
    }

    @Test
    fun `getting correct when password have less than 8 characters`(){
        val password = "pass"
        val result = ValidatePassword().execute(password)
        val expectedErrorMessage = "The password need to consist of at least 8 characters"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }
}