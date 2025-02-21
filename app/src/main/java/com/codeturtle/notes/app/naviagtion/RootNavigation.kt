package com.codeturtle.notes.app.naviagtion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeturtle.notes.authentication.navigation.authNavGraph
import com.codeturtle.notes.notes.navigation.noteNavGraph

@Composable
fun RootNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) Destinations.Note.route else Destinations.Auth.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        authNavGraph(navController = navController)
        noteNavGraph(navController = navController)
    }
}
