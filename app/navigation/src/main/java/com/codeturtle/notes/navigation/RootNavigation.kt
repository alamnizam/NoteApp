package com.codeturtle.notes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) Destinations.Note.route else Destinations.Auth.route
    ) {
        authNavGraph(navController = navController)
        noteNavGraph(navController = navController)
    }
}
