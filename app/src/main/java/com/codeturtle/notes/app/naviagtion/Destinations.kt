package com.codeturtle.notes.app.naviagtion

import com.codeturtle.notes.authentication.navigation.AuthNavGraph

sealed class Destinations<T> (
    val route: T
){
    data object Auth : Destinations<AuthNavGraph>(AuthNavGraph)
}