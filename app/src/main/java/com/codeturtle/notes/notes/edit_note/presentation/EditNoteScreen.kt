package com.codeturtle.notes.notes.edit_note.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.codeturtle.notes.notes.navigation.EditNoteScreen

@Composable
fun EditNoteScreen(
    navController: NavHostController,
    viewModel: EditNoteViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    note: EditNoteScreen
) {

}