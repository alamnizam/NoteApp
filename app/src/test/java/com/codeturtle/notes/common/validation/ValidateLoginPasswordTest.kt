package com.codeturtle.notes.common.validation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidateLoginPasswordTest{
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Password is empty`() {
        val password = ""
        val result = ValidatePassword().execute(password)
        assert(!result.success)
    }

    @Test
    fun `Password is not empty`() {
        val password = "Password@123"
        val result = ValidatePassword().execute(password)
        assert(result.success)
    }

    @Test
    fun `getting correct error message when password is empty`(){
        val password = ""
        val result = ValidatePassword().execute(password)
        val expectedErrorMessage = "The password can't be blank"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }
}