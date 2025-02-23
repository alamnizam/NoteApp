package com.codeturtle.notes.notes.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.notes.add_note.presentation.AddNoteScreen
import com.codeturtle.notes.notes.notes_list.presentation.NoteListScreen

fun NavGraphBuilder.noteNavGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    navigation<NoteNavGraph>(startDestination = NoteListScreen) {
        composable<NoteListScreen> {
            NoteListScreen(navController = navController, snackBarHostState = snackBarHostState)
        }
        composable<AddNoteScreen> {
            AddNoteScreen(navController = navController, snackBarHostState = snackBarHostState)
        }
    }
}