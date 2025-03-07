package com.codeturtle.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.codeturtle.notes.common.utils.CustomNavType
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.presentation.NoteListScreen
import com.codeturtle.notes.presentation.AddNoteScreen
import com.codeturtle.notes.presentation.EditNoteScreen
import com.codeturtle.notes.presentation.NoteDetailScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.noteNavGraph(
    navController: NavHostController
) {
    navigation<NoteNavGraph>(startDestination = NoteListScreen) {
        composable<NoteListScreen> {
            NoteListScreen(navController = navController)
        }
        composable<AddNoteScreen> {
            AddNoteScreen(navController = navController)
        }
        composable<NoteSearchScreen> {
//            NoteSearchScreen(navController = navController)
        }
        composable<NoteDetailScreen>(
            typeMap = mapOf(
                typeOf<NoteListResponseItem>() to CustomNavType(
                    NoteListResponseItem::class,
                    NoteListResponseItem.serializer()
                )
            )
        ) {
            val note = it.toRoute<NoteDetailScreen>()
            NoteDetailScreen(navController = navController,note = note)
        }
        composable<EditNoteScreen>(
            typeMap = mapOf(
                typeOf<NoteListResponseItem>() to CustomNavType(
                    NoteListResponseItem::class,
                    NoteListResponseItem.serializer()
                )
            )
        ) {
            val note = it.toRoute<EditNoteScreen>()
            EditNoteScreen(navController = navController,note = note)
        }
    }
}