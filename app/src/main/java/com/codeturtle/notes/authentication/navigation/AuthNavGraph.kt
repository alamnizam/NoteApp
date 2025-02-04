package com.codeturtle.notes.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.authentication.login.presentation.LoginScreen
import com.codeturtle.notes.authentication.registration.presentation.RegistrationScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation<AuthNavGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(navController)
        }
        composable<RegisterScreen> {
            RegistrationScreen(navController = navController)
        }
    }
}