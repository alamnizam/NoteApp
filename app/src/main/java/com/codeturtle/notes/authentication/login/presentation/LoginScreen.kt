package com.codeturtle.notes.authentication.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import com.codeturtle.notes.authentication.navigation.RegisterScreen

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    Box{
        Button(
            onClick = {
                navController.navigate(RegisterScreen)
            },
            modifier = Modifier.testTag("Login")
        ) {
            Text(text = "Login")
        }
    }
}