package com.codeturtle.notes.navigation

import com.codeturtle.notes.authentication.navigation.AuthNavGraph
import com.codeturtle.notes.notes.navigation.NoteNavGraph

sealed class Destinations<T> (
    val route: T
){
    data object Auth : Destinations<AuthNavGraph>(AuthNavGraph)
    data object Note : Destinations<NoteNavGraph>(NoteNavGraph)
}