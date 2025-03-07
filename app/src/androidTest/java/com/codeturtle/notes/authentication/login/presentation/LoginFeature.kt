package com.codeturtle.notes.authentication.login.presentation

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.codeturtle.notes.R
import com.codeturtle.notes.MainActivity
import com.codeturtle.notes.authentication.login.mockwebserver.LoginMockServerDispatcher
import com.codeturtle.notes.common.constant.ServerUrlList.LOGIN
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
class LoginFeature {

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

    private val successServiceMap = mapOf(
        "/$LOGIN" to "auth_login_success.json"
    )

    private val emailPasswordErrorServiceMap = mapOf(
        "/$LOGIN" to "auth_login_failed.json"
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
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun validateLoginTitleTextDisplayed(){
        composeRule.apply {
            onNodeWithText(context.getString(R.string.user_login)).assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginSubTitleTextIsDisplayed() {
        composeRule.apply {
                onNodeWithText(context.getString(R.string.please_enter_your_details_to_login)).assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginFormIsDisplayed(){
        composeRule.apply{
            onNodeWithTag("LoginForm").assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginFormEmailTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginFormPasswordTextFieldIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginFormLoginButtonIsDisplayed() {
        composeRule.apply {
            onNodeWithTag("Login").assertIsDisplayed()
        }
    }

    @Test
    fun validateDoNotHaveAccountTextWhenClickRegistrationScreenOpens(){
        composeRule.apply {
            onNodeWithTag("Goto RegistrationScreen").assertIsDisplayed()
            onNodeWithTag("Goto RegistrationScreen").performClick()
            onNodeWithText(context.resources.getString(R.string.registration)).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_email_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormEmailTextFieldInvalidAndHasError() {
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992gmail.com")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.that_s_not_a_valid_email)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateRegisterFormPasswordTextFieldEmptyAndHasError() {
        composeRule.apply {
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            onNodeWithText(with(context) {
                resources.getString(R.string.the_password_can_t_be_blank)
            }).assertIsDisplayed()
        }
    }

    @Test
    fun validateProgressBarIsDisplayed() {
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            onNodeWithTag("progress").assertIsDisplayed()
        }
    }



    @Test
    fun validateLoginFormButtonIsClickAndCheckNoteListIsShowing() {
        mockServer.dispatcher = LoginMockServerDispatcher().successDispatcher(successServiceMap)
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            val request: RecordedRequest = mockServer.takeRequest()
            assertEquals("/${LOGIN}", request.path)
            waitForIdle()
            Thread.sleep(3000)
            onNodeWithText(context.resources.getString(R.string.note_list)).assertIsDisplayed()
        }
    }

    @Test
    fun validateLoginFormButtonIsClickAndShowFailureSnackBar() {
        mockServer.dispatcher = LoginMockServerDispatcher().emailPasswordErrorDispatcher(emailPasswordErrorServiceMap)
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            val request: RecordedRequest = mockServer.takeRequest()
            assertEquals("/${LOGIN}", request.path)
            waitForIdle()
            Thread.sleep(3000)
            onNodeWithText("Wrong email/password").assertIsDisplayed()
        }
    }
}