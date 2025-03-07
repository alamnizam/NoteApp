package com.codeturtle.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.presentation.LoginScreen
import com.codeturtle.notes.presentation.RegistrationScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<AuthNavGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(navController = navController)
        }
        composable<RegisterScreen> {
            RegistrationScreen(navController = navController)
        }
    }
}