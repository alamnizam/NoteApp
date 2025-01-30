package com.codeturtle.notes.authentication.registration.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val registerResponse = viewModel.registerResponse.value
    val responseEvent = viewModel.responseEvent.collectAsState(initial = null)
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            responseEvent.value.let {
                if (registerResponse.isLoading) {
                    ProgressBar()
                }
                if (registerResponse.errorMessage.isNotBlank()) {
                    Toast.makeText(
                        context,
                        registerResponse.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (registerResponse.errorData != null) {
                    Toast.makeText(
                        context,
                        registerResponse.errorData.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (registerResponse.data != null) {
                    Toast.makeText(
                        context,
                        registerResponse.data.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Register(
                uiState = uiState.value,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }

    }
}

@Composable
fun Register(
    uiState: RegistrationUIState,
    onEvent: (RegistrationUIEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Greeting()
        Spacer(Modifier.height(10.dp))
        RegistrationForm(uiState, onEvent)
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
            label = { Text("User Name") },
            isError = uiState.userNameError != null,
            supportingText = {
                uiState.userNameError?.asString()?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Email")
                .fillMaxWidth(),
            value = uiState.email,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.EmailChanged(it)) },
            label = { Text("Email") },
            isError = uiState.emailError != null,
            supportingText = {
                uiState.emailError?.asString()?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Password")
                .fillMaxWidth(),
            value = uiState.password,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            isError = uiState.passwordError != null,
            supportingText = {
                uiState.passwordError?.asString()?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .testTag("Confirm Password")
                .fillMaxWidth(),
            value = uiState.confirmPassword,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(RegistrationUIEvent.ConfirmPasswordChanged(it)) },
            label = { Text("Confirm Password") },
            isError = uiState.confirmPasswordError != null,
            supportingText = {
                uiState.confirmPasswordError?.asString()?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .testTag("Register")
                .fillMaxWidth(),
            onClick = { onEvent(RegistrationUIEvent.RegisterButtonClicked) },
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