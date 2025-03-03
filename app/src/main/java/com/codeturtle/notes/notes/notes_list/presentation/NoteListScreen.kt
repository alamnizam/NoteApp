package com.codeturtle.notes.notes.notes_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codeturtle.notes.R
import com.codeturtle.notes.authentication.navigation.AuthNavGraph
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.codeturtle.notes.notes.navigation.NoteNavGraph
import com.codeturtle.notes.notes.navigation.NoteSearchScreen
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavHostController,
    viewModel: NoteListViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val noteListResponse = viewModel.noteListResponse.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.searchIconEvent.collect {
            navController.navigate(NoteSearchScreen)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.logoutIconEvent.collect {
            scope.launch {
                viewModel.tokenManager.clearData()
            }
            navController.popBackStack(
                route = NoteNavGraph,
                inclusive = true
            )
            navController.navigate(AuthNavGraph)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.addNoteEvent.collect {
            navController.navigate(NoteSearchScreen)
        }
    }

    NoteList(
        noteListResponse = noteListResponse,
        onEvent = {
            viewModel.onEvent(it)
        },
        snackBarHostState = snackBarHostState
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteList(
    onEvent: (NoteListUIEvent) -> Unit,
    snackBarHostState: SnackbarHostState,
    noteListResponse: NoteListState
) {
    val scope = rememberCoroutineScope()
    val isPlaying by remember { mutableStateOf(true) }
    val speed by remember { mutableFloatStateOf(1F) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_list_lottie))


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("Add"),
                onClick = { onEvent(NoteListUIEvent.AddNoteClicked) },
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.add_note)
                )
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("topAppBar"),
                title = {
                    Text(
                        text = stringResource(R.string.note_list),
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag("Search"),
                        onClick = { onEvent(NoteListUIEvent.SearchIconClicked) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = stringResource(R.string.search_icon)
                        )
                    }
                    IconButton(
                        modifier = Modifier.testTag("Logout"),
                        onClick = { onEvent(NoteListUIEvent.LogoutIconClicked) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            if (noteListResponse.isLoading) {
                ProgressBar()
            }
            if (noteListResponse.errorMessage.isNotBlank()) {
                LaunchedEffect(key1 = true) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = noteListResponse.errorMessage
                            )
                        )
                    }
                }
            }
            if (noteListResponse.dataError != null) {
                LaunchedEffect(key1 = true) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = noteListResponse.dataError.message
                            )
                        )
                    }
                }
            }
            if (noteListResponse.data != null) {
                if (noteListResponse.data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.testTag("NoteList")
                    ) {
                        items(noteListResponse.data) {
                            NoteListItem(note = it)
                        }
                    }
                } else {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier
                            .size(400.dp)
                            .testTag("LottieAnimation"),
                        speed = speed,
                        iterations = LottieConstants.IterateForever,
                        isPlaying = isPlaying,
                        restartOnPlay = false
                    )
                }
            }
        }
    }
}

@Composable
fun NoteListItem(note: NoteListResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.noteTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = convertLongToTime(note.date),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(Modifier.padding(5.dp))
            Text(
                text = note.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun NoteListItemPreview() {
    val noteList = listOf(
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › kotlin-convert-t. I'm trying to find out how I can convert timestamp to datetime in Kotlin, this is very simple in Java but I cant find any equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        ),
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › kotlin-convert-t. I'm trying to find out how I can convert timestamp to datetime in Kotlin, this is very simple in Java but I cant find any equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        ),
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › kotlin-convert-t. I'm trying to find out how I can convert timestamp to datetime in Kotlin, this is very simple in Java but I cant find any equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        )
    )
    NoteList(
        noteListResponse = NoteListState(
            data = noteList
        ),
        onEvent = {},
        snackBarHostState = SnackbarHostState()
    )
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(timeStamp: Long): String {
    val date = Date(timeStamp * 1000)
    val format = SimpleDateFormat("dd-MMM-yyyy")
    return format.format(date)
}
