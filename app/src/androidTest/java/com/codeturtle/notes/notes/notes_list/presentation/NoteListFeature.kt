package com.codeturtle.notes.notes.notes_list.presentation

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
import com.codeturtle.notes.app.MainActivity
import com.codeturtle.notes.authentication.login.mockwebserver.LoginMockServerDispatcher
import com.codeturtle.notes.common.constant.Pref.TEST_DATA_STORE_FILE_NAME
import com.codeturtle.notes.common.constant.ServerUrlList.LOGIN
import com.codeturtle.notes.common.preference.tokken.TokenManager
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
class NoteListFeature {
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

    @Inject
    lateinit var tokenManager: TokenManager

    private val successLoginServiceMap = mapOf(
        "/$LOGIN" to "auth_login_success.json"
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

        doLogin()
        composeRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeRule.onNodeWithTag("topAppBar").fetchSemanticsNode()
                true
            } catch (e: AssertionError) {
                false
            }
        }

    }

    private fun doLogin() {
        mockServer.dispatcher = LoginMockServerDispatcher().successDispatcher(successLoginServiceMap)
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
            val request: RecordedRequest = mockServer.takeRequest()
            assertEquals("/${LOGIN}", request.path)
            waitForIdle()
        }
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
        ApplicationProvider.getApplicationContext<Context>().deleteFile(TEST_DATA_STORE_FILE_NAME)
    }

    @Test
    fun validateNoteListScreenHaveTopBarWithTextNote(){
        composeRule.apply {
            onNodeWithText(resources.getString(R.string.note_list)).assertIsDisplayed()
        }
    }

    @Test
    fun validateNoteListScreenHaveTopBarWithSearchIcon(){
        composeRule.apply {
            onNodeWithTag("Search").assertIsDisplayed()
        }
    }

    @Test
    fun validateNoteListScreenHaveTopBarWithLogoutIcon(){
        composeRule.apply {
            onNodeWithTag("Logout").assertIsDisplayed()
        }
    }

    @Test
    fun validateNoteListScreenHaveFloatingActionButtonToAddNote() {
        composeRule.apply {
            onNodeWithTag("Add").assertIsDisplayed()
        }
    }
}