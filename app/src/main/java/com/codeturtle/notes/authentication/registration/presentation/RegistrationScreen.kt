package com.codeturtle.notes.authentication.registration.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codeturtle.notes.authentication.registration.data.model.RegisterResponse
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navController:NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val registerResponse = viewModel.registerResponse.value
    val responseEvent = viewModel.responseEvent.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            responseEvent.value.let {
                if (registerResponse.isLoading) {
                    ProgressBar()
                }
                if (registerResponse.errorMessage.isNotBlank()) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = registerResponse.errorMessage
                            )
                        )
                    }
                }
                if (registerResponse.data != null) {
                    if (registerResponse.data.body() != null) {
                        scope.launch {
                            SnackBarController.sendEvent(
                                event = SnackBarEvent(
                                    message = registerResponse.data.body()?.message.toString()
                                )
                            )
                        }
                    } else {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            registerResponse.data.errorBody()?.string(),
                            RegisterResponse::class.java
                        )
                        scope.launch {
                            SnackBarController.sendEvent(
                                event = SnackBarEvent(
                                    message = errorResponse.message
                                )
                            )
                        }
                    }

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