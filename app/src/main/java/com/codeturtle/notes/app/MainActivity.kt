package com.codeturtle.notes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.codeturtle.notes.app.naviagtion.RootNavigation
import com.codeturtle.notes.app.ui.theme.NotesTheme
import com.codeturtle.notes.common.preference.tokken.TokenManager
import com.codeturtle.notes.common.snakbar.ObserveAsEvents
import com.codeturtle.notes.common.snakbar.SnackBarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)
        setContent {
            NotesTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                ObserveAsEvents(
                    flow = SnackBarController.events,
                    snackBarHostState
                ) { event ->
                    scope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        val result = snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }
                Scaffold (
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
                ){innerPadding ->
                    RootNavigation(
                        navController = navController,
                        isLoggedIn = isLoggedIn,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}