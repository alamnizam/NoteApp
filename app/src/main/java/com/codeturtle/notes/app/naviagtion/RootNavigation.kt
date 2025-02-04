package com.codeturtle.notes.app.naviagtion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeturtle.notes.authentication.navigation.authNavGraph

@Composable
fun RootNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Auth.route,
        modifier = Modifier.padding(innerPadding)
    ){
        authNavGraph(navController = navController)
    }
}
