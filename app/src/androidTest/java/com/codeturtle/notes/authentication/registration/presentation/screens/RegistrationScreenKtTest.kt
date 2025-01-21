package com.codeturtle.notes.authentication.registration.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.codeturtle.notes.MainActivity
import org.junit.Rule
import org.junit.Test

class RegistrationScreenKtTest {
    @get: Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validateRegisterTitleTextIsDisplayed() {
        composeRule.apply {
            onNodeWithText("Registration").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterSubTitleTextIsDisplayed() {
        composeRule.apply {
            onNodeWithText("Please enter your details to register").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("RegistrationForm").assertIsDisplayed()
        }

    }

    @Test
    fun validateRegisterFormUserNameTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("User Name").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormConfirmPasswordTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Confirm Password").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormRegisterButtonIsDisplayed() {
        composeRule.apply {
            onNodeWithText("Register").assertIsDisplayed()
        }
    }
}