package com.codeturtle.notes.authentication.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.navigation.AuthNavGraph
import com.codeturtle.notes.authentication.navigation.RegisterScreen
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.codeturtle.notes.notes.navigation.NoteNavGraph
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loginResponse = viewModel.loginResponse.value
    val responseEvent = viewModel.responseEvent.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = Modifier
            .fillMaxSize()
    ) {innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ){
            LaunchedEffect(key1 = true) {
                viewModel.registerClickEvent.collect {
                    navController.navigate(RegisterScreen)
                }
            }

            responseEvent.value.let {
                if (loginResponse.isLoading) {
                    ProgressBar()
                }
                if (loginResponse.errorMessage.isNotBlank()) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = loginResponse.errorMessage
                            )
                        )
                    }
                }
                if (loginResponse.data != null) {
                    scope.launch {
                        viewModel.tokenManager.saveToken(loginResponse.data.message)
                        viewModel.tokenManager.saveIsLoggedIn(true)
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = context.getString(R.string.user_logged_in_successfully)
                            )
                        )
                    }
                    navController.popBackStack(
                        route = AuthNavGraph,
                        inclusive = true
                    )
                    navController.navigate(NoteNavGraph)
                }
                if (loginResponse.errorData != null) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = loginResponse.errorData.message
                            )
                        )
                    }
                }
            }

            Login(
                uiState = uiState.value,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }
    }
}

@Composable
fun Login(
    uiState: LoginUIState,
    onEvent: (LoginUIEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        LoginGreeting()
        Spacer(Modifier.height(10.dp))
        LoginForm(uiState,onEvent)
    }
}

@Composable
fun LoginGreeting() {
    Text(
        text = stringResource(R.string.user_login),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(10.dp))
    Text(
        text = stringResource(R.string.please_enter_your_details_to_login),
        fontSize = 16.sp
    )
}

@Composable
fun LoginForm(
    uiState: LoginUIState,
    onEvent: (LoginUIEvent) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.testTag("LoginForm")
    ) {
        OutlinedTextField(
            modifier = Modifier
                .testTag("Email")
                .fillMaxWidth(),
            value = uiState.email,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onEvent(LoginUIEvent.EmailChanged(it)) },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
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
            onValueChange = { onEvent(LoginUIEvent.PasswordChanged(it)) },
            label = { Text(stringResource(R.string.password)) },
            singleLine = true,
            isError = uiState.passwordError != null,
            supportingText = {
                uiState.passwordError?.asString()?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                val image = when {
                    passwordVisible -> Icons.Filled.Visibility
                    else -> Icons.Filled.VisibilityOff
                }
                val description = when {
                    passwordVisible -> stringResource(R.string.hide_password)
                    else -> stringResource(R.string.show_password)
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = when {
                !passwordVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            }
        )
        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .testTag("Login")
                .fillMaxWidth(),
            onClick = { onEvent(LoginUIEvent.LoginButtonClicked) },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
        ) {
            Text(stringResource(R.string.login))
        }
        Spacer(Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag("Goto RegistrationScreen")
                .clickable { onEvent(LoginUIEvent.RegisterTextClicked) },
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                ) {
                    append(stringResource(R.string.don_t_have_an_account))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight(500)
                    )
                ) {
                    append(stringResource(R.string.register))
                }
            },
            fontSize = 16.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    Login(
        uiState = LoginUIState(),
        onEvent = {}
    )
}
