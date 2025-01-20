package com.codeturtle.notes.authentication.registration.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codeturtle.notes.authentication.registration.presentation.event.RegistrationUIEvent
import com.codeturtle.notes.authentication.registration.presentation.state.RegistrationUIState
import com.codeturtle.notes.authentication.registration.presentation.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Register(
        uiState = uiState.value,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
fun Register(
    uiState: RegistrationUIState,
    onEvent: (RegistrationUIEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Greeting()
            Spacer(Modifier.height(10.dp))
            RegistrationForm(uiState,onEvent)
        }

    }
}

@Composable
private fun Greeting() {
    Text(
        text = "Registration",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(10.dp))
    Text(
        text = "Please enter your details to register",
        fontSize = 16.sp
    )
}

@Composable
fun RegistrationForm(
    uiState: RegistrationUIState,
    onEvent: (RegistrationUIEvent) -> Unit
) {

    Column(modifier = Modifier.testTag("RegistrationForm")) {
        OutlinedTextField(
            modifier = Modifier
                .testTag("User Name")
                .fillMaxWidth(),
            value = uiState.userName,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.UserNameChanged(it)) },
            label = { Text("User Name") }
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Email")
                .fillMaxWidth(),
            value = uiState.email,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.EmailChanged(it)) },
            label = { Text("Email") }
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Password")
                .fillMaxWidth(),
            value = uiState.password,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.PasswordChanged(it)) },
            label = { Text("Password") }
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Confirm Password")
                .fillMaxWidth(),
            value = uiState.confirmPassword,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.ConfirmPasswordChanged(it)) },
            label = { Text("Confirm Password") }
        )
        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .testTag("Register")
                .fillMaxWidth(),
            onClick = { },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
        ) {
            Text("Register")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    Register(
        uiState = RegistrationUIState(),
        onEvent = {}
    )
}