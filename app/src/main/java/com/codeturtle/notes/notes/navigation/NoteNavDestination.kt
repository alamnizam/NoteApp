package com.codeturtle.notes.notes.navigation

import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import kotlinx.serialization.Serializable

@Serializable
object NoteNavGraph
@Serializable
object AddNoteScreen
@Serializable
object NoteListScreen
@Serializable
object NoteSearchScreen
@Serializable
data class NoteDetailScreen(val note:NoteListResponseItem)