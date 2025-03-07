package com.codeturtle.notes.navigation

sealed class Destinations<T> (
    val route: T
){
    data object Auth : Destinations<AuthNavGraph>(AuthNavGraph)
    data object Note : Destinations<NoteNavGraph>(NoteNavGraph)
}