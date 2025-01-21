package com.codeturtle.notes.authentication.registration.domain.use_case.validation

import android.content.Context
import android.content.res.Resources
import android.os.Build.VERSION_CODES.Q
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
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