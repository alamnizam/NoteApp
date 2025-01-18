package com.codeturtle.notes.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.codeturtle.notes.MainActivity
import org.junit.Rule
import org.junit.Test

class RegistrationFeature {

    @get: Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validateRegisterTitleTextIsDisplayed() {
        composeRule.apply {
            onNodeWithText("Registration").assertIsDisplayed()
        }
    }
}