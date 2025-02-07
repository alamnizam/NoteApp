package com.codeturtle.notes.authentication.registration.presentation.screens

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.codeturtle.notes.R
import com.codeturtle.notes.app.MainActivity
import com.codeturtle.notes.authentication.registration.data.mockserver.MockServerDispatcher
import com.codeturtle.notes.common.constant.ServerUrlList.REGISTER
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RegistrationFeature {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Inject
    lateinit var okHttp: OkHttpClient
    private lateinit var okHttp3IdlingResource: OkHttp3IdlingResource
    private lateinit var mockServer: MockWebServer
    private val successServiceMap: Map<String, String> = mapOf(
        Pair("/$REGISTER", "auth_register_success.json")
    )

    private val emailErrorServiceMap: Map<String, String> = mapOf(
        Pair("/$REGISTER", "auth_register_email_error.json")
    )

    @Before
    fun setUp() {
        hiltRule.inject()
        okHttp3IdlingResource = OkHttp3IdlingResource.create("okhttp", okHttp)
        IdlingRegistry.getInstance().register(okHttp3IdlingResource)
        mockServer = MockWebServer()
        mockServer.start(8080)

        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
        composeRule.apply {
            onNodeWithTag("Goto RegistrationScreen").isDisplayed()
            onNodeWithTag("Goto RegistrationScreen").performClick()
        }
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun validateRegisterTitleTextIsDisplayed() {
        composeRule.apply {
            onNodeWithText(context.resources.getString(R.string.registration)).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterSubTitleTextIsDisplayed() {
        composeRule.apply {
            onNodeWithText(context.resources.getString(R.string.please_enter_your_details_to_register)).assertIsDisplayed()
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
            onNodeWithText(context.resources.getString(R.string.register)).assertIsDisplayed()
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
            onNodeWithText(with(context) {
                resources.getString(R.string.the_email_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldInvalidAndHasError() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992gmail.com")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.that_s_not_a_valid_email)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_password_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldInvalidAndHasErrorOfLength() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_password_need_to_consist_of_at_least_8_characters)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldInvalidAndHasErrorOf1SpecialCharacter1UpperCaseAnd1Number() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("alamnizam")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_password_must_have_1_special_character_1_number_1_uppercase_and_1_lowercase_character)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormConfirmPasswordTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_confirm_password_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormConfirmPasswordTextFieldInvalidAndHasErrorOfPasswordNotMatch() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_passwords_don_t_match)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateProgressBarIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("User Name").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            onNodeWithTag("progress").assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormButtonIsClickAndShowSuccessSnackBar() {
        mockServer.dispatcher = MockServerDispatcher().successDispatcher(successServiceMap)
        composeRule.apply {
            onNodeWithTag("RegistrationForm").assertIsDisplayed()
            onNodeWithTag("User Name").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            val request: RecordedRequest = mockServer.takeRequest()
            assertEquals("/$REGISTER", request.path)
            onNodeWithText(context.getString(R.string.user_registered_successfully)).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormButtonIsClickAndShowEmailAlreadyExistsSnackBar() {
        mockServer.dispatcher = MockServerDispatcher().emailErrorDispatcher(emailErrorServiceMap)
        composeRule.apply {
            onNodeWithTag("RegistrationForm").assertIsDisplayed()
            onNodeWithTag("User Name").assertIsDisplayed().performTextInput("Nizam")
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Confirm Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Register").assertIsDisplayed().performClick()
            val request: RecordedRequest = mockServer.takeRequest()
            assertEquals("/$REGISTER", request.path)
            onNodeWithText("(alamnizam1992@gmail.com)-email already exists").assertIsDisplayed()
        }
    }

    @Test
    fun validateAlreadyHaveAccountTextIsShowing() {
        composeRule.apply {
            onNodeWithTag("Goto LoginScreen").assertIsDisplayed()
        }
    }

    @Test
    fun validateAlreadyHaveAccountTextWhenClickLoginScreenOpens(){
        composeRule.apply {
            onNodeWithTag("Goto LoginScreen").assertIsDisplayed()
            onNodeWithTag("Goto LoginScreen").performClick()
        }
    }
}