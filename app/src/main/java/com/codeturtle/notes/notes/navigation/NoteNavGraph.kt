package com.codeturtle.notes.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.notes.add_note.presentation.AddNoteScreen
import com.codeturtle.notes.notes.notes_list.presentation.NoteListScreen

fun NavGraphBuilder.noteNavGraph(navController: NavHostController) {
    navigation<NoteNavGraph>(startDestination = NoteListScreen) {
        composable<NoteListScreen> {
            NoteListScreen(navController = navController)
        }
        composable<AddNoteScreen> {
            AddNoteScreen(navController = navController)
        }
    }
}