package com.codeturtle.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codeturtle.notes.authentication.registration.presentation.RegistrationScreen
import com.codeturtle.notes.authentication.registration.presentation.RegistrationViewModel
import com.codeturtle.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                val viewModel = viewModel<RegistrationViewModel>()
                RegistrationScreen(viewModel = viewModel)
            }
        }
    }
}