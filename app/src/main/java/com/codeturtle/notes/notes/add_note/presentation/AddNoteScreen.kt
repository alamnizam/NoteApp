package com.codeturtle.notes.notes.add_note.presentation

import android.content.res.Configuration
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codeturtle.notes.R
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.codeturtle.notes.notes.navigation.NoteListScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddNoteScreen(
    navController: NavHostController,
    viewModel: AddNoteViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val addNoteResponse = viewModel.addNoteResponse.value
    val responseEvent = viewModel.responseEvent.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.backArrowIconClickedEvent.collect {
            navController.navigateUp()
        }
    }

    responseEvent.value.let {
        if (addNoteResponse.isLoading) {
            ProgressBar()
        }
        if (addNoteResponse.errorMessage.isNotBlank()) {
            scope.launch {
                SnackBarController.sendEvent(
                    event = SnackBarEvent(
                        message = addNoteResponse.errorMessage
                    )
                )
            }
        }
        if (addNoteResponse.data != null) {
            scope.launch {
                val job = launch {
                    SnackBarController.sendEvent(
                        event = SnackBarEvent(
                            message = context.getString(R.string.note_added_successfully)
                        )
                    )
                }
                job.join()
                withContext(Dispatchers.Main) {
                    navController.popBackStack(
                        route = NoteListScreen,
                        inclusive = true
                    )
                    navController.navigate(NoteListScreen)

                }
            }
        }
        if (addNoteResponse.errorData != null) {
            scope.launch {
                SnackBarController.sendEvent(
                    event = SnackBarEvent(
                        message = addNoteResponse.errorData.message
                    )
                )
            }
        }
    }

    AddNote(
        uiState = uiState.value,
        onEvent = {
            viewModel.onEvent(it)
        },
        snackBarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNote(
    uiState: AddNoteUIState,
    onEvent: (AddNoteUIEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.add_note),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(AddNoteUIEvent.OnBackNavigationClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_navigation)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        contentWindowInsets = WindowInsets.safeContent, modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Button(
                onClick = { onEvent(AddNoteUIEvent.OnSaveNoteClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.save_note))
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
        ) {
            BasicTextField(
                value = uiState.title,
                onValueChange = { onEvent(AddNoteUIEvent.OnTitleChanged(it)) },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (uiState.title.isEmpty()) Text(
                        stringResource(R.string.title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    innerTextField()
                }
            )
            if (uiState.titleError != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.titleError.asString(),
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                LaunchedEffect(uiState.description) {
                    coroutineScope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
                BasicTextField(
                    value = uiState.description,
                    onValueChange = { onEvent(AddNoteUIEvent.OnDescriptionChanged(it)) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.fillMaxSize(),
                    decorationBox = { innerTextField ->
                        if (uiState.description.isEmpty())
                            Text(
                                text = stringResource(R.string.description),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        innerTextField()
                    }
                )
            }
            if (uiState.descriptionError != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.descriptionError.asString(),
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddNotePrev() {
    AddNote(
        uiState = AddNoteUIState(),
        onEvent = {},
        snackBarHostState = SnackbarHostState()
    )
}

