package com.codeturtle.notes.authentication.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeturtle.notes.authentication.login.presentation.LoginScreen
import com.codeturtle.notes.authentication.registration.presentation.RegistrationScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    navigation<AuthNavGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(navController = navController,snackBarHostState = snackBarHostState)
        }
        composable<RegisterScreen> {
            RegistrationScreen(navController = navController,snackBarHostState = snackBarHostState)
        }
    }
}