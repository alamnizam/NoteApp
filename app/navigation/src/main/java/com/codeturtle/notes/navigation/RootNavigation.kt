package com.codeturtle.notes.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean,
    innerPadding: PaddingValues
) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = if(isLoggedIn) Destinations.Note.route else Destinations.Auth.route
    ) {
        authNavGraph(navController = navController)
        noteNavGraph(navController = navController)
    }
}
