package com.codeturtle.notes.app.naviagtion

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeturtle.notes.authentication.navigation.authNavGraph
import com.codeturtle.notes.notes.navigation.noteNavGraph

@Composable
fun RootNavigation(
    navController: NavHostController,
    snackBarHostState : SnackbarHostState,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) Destinations.Note.route else Destinations.Auth.route
    ) {
        authNavGraph(navController = navController,snackBarHostState)
        noteNavGraph(navController = navController,snackBarHostState)
    }
}
