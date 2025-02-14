package com.codeturtle.notes.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.authentication.login.presentation.LoginScreen
import com.codeturtle.notes.authentication.registration.presentation.RegistrationScreen

fun NavGraphBuilder.noteNavGraph(navController: NavHostController) {
    navigation<NoteNavGraph>(startDestination = NoteListScreen) {
        composable<NoteListScreen> {
            LoginScreen(navController = navController)
        }
        composable<AddNoteScreen> {
            RegistrationScreen(navController = navController)
        }
    }
}