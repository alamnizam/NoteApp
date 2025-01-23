package com.codeturtle.notes.authentication.registration.presentation.screens

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.codeturtle.notes.MainActivity
import com.codeturtle.notes.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegistrationFeature {
    @get: Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
    }

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

    @Test
    fun validateRegisterFormUserNameTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("User Name").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_username_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_email_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldInvalidAndHasError() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992gmail.com")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.that_s_not_a_valid_email)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_password_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldInvalidAndHasErrorOfLength() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_password_need_to_consist_of_at_least_8_characters)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldInvalidAndHasErrorOf1SpecialCharacter1UpperCaseAnd1Number() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("alamnizam")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_password_must_have_1_special_character_1_number_1_uppercase_and_1_lowercase_character)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormConfirmPasswordTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_confirm_password_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormConfirmPasswordTextFieldInvalidAndHasErrorOfPasswordNotMatch() {
        composeRule.apply{
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context){
                resources.getString(R.string.the_passwords_don_t_match)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormButtonIsClickAndShowSuccessMessage() {
        composeRule.apply {
            onNodeWithTag("User Name").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText("Success").assertIsDisplayed()
        }
    }
}