package com.codeturtle.notes.common.validation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidateConfirmPasswordTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Confirm password is empty`() {
        val password = "Password@123"
        val confirmPassword = ""
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        assert(!result.success)
    }

    @Test
    fun `Confirm password is not empty`() {
        val password = "Password@123"
        val confirmPassword = "Password@123"
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        assert(result.success)
    }

    @Test
    fun `getting correct error message when confirm password is empty`(){
        val password = "Password@123"
        val confirmPassword = ""
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        val expectedErrorMessage = "The confirm password can't be blank"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }

    @Test
    fun `password is not equal to confirm password`() {
        val password = "Password@123"
        val confirmPassword = "Password@12"
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        assert(!result.success)
    }

    @Test
    fun `password is equal to confirm password`() {
        val password = "Password@123"
        val confirmPassword = "Password@123"
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        assert(result.success)
    }

    @Test
    fun `getting correct error message when password is not equal to confirm password`(){
        val password = "Password@123"
        val confirmPassword = "Password@12"
        val result = ValidateConfirmPassword().execute(password,confirmPassword)
        val expectedErrorMessage = "The passwords don't match"
        assert(result.errorMessage?.asString(context)?.equals(expectedErrorMessage) == true)
    }
}