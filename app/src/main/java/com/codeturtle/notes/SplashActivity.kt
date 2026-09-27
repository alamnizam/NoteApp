package com.codeturtle.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codeturtle.notes.common.token.TokenManager
import com.codeturtle.notes.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition { true }
        setContent {
            NotesTheme {
                SplashScreenContent {
                    val isLoggedIn = tokenManager.getIsLoggedIn()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("isLoggedIn", isLoggedIn)
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreenContent(onTimeout: suspend () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2.seconds)
        onTimeout()
    }
}
