package com.codeturtle.notes.notes.notes_list.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.codeturtle.notes.app.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule

class NoteListFeature {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    fun doLogin(){
        composeRule.apply {
            onNodeWithTag("Email").assertIsDisplayed().performTextInput("alamnizam1992@gmail.com")
            onNodeWithTag("Password").assertIsDisplayed().performTextInput("Nizam@123")
            onNodeWithTag("Login").assertIsDisplayed().performClick()
        }
    }

//    @Test
//    fun validateNoteListScreenHaveTopBarWithTextNote(){
//
//    }
}