package com.codeturtle.notes.app.naviagtion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeturtle.notes.app.MainViewModel
import com.codeturtle.notes.authentication.navigation.authNavGraph
import com.codeturtle.notes.notes.navigation.noteNavGraph

@Composable
fun RootNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    splashScreen: SplashScreen
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val isLoggedIn = mainViewModel.isLoggedIn.collectAsState()
    if (isLoggedIn.value == null) {
        splashScreen.setKeepOnScreenCondition { true }
    } else {
        splashScreen.setKeepOnScreenCondition { false }
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn.value == true) {
                Destinations.Note.route
            } else {
                Destinations.Auth.route
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            authNavGraph(navController = navController)
            noteNavGraph(navController = navController)
        }
    }
}
