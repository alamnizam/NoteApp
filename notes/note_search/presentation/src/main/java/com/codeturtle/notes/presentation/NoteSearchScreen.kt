package com.codeturtle.notes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codeturtle.notes.common.R
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.component.SearchView
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.codeturtle.notes.common.utils.HandleDate.convertLongToDate
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.navigation.NoteDetailScreen
import kotlinx.coroutines.launch

@Composable
fun NoteSearchScreen(
    navController: NavHostController,
    viewModel: NoteSearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val noteListResponse = viewModel.noteListResponse.value

    LaunchedEffect(key1 = true) {
        viewModel.backArrowIconClickedEvent.collect {
            navController.navigateUp()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.noteClickedEvent.collect {
            it.note?.let { note ->
                navController.navigate(NoteDetailScreen(note = note))
            }
        }
    }

    NoteList(
        uiState = uiState.value,
        onEvent = {
            viewModel.onEvent(it)
        },
        noteListResponse = noteListResponse
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteList(
    uiState: NoteSearchUIState,
    onEvent: (NoteSearchUIEvent) -> Unit,
    noteListResponse: NoteSearchState
) {
    val scope = rememberCoroutineScope()
    val isPlaying by remember { mutableStateOf(true) }
    val speed by remember { mutableFloatStateOf(1F) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_list_lottie))


    Scaffold(
        contentWindowInsets = WindowInsets.safeContent,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("topAppBar"),
                title = {
                    Text(
                        text = stringResource(R.string.note_search),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag("BackNavigation"),
                        onClick = { onEvent(NoteSearchUIEvent.OnBackNavigationClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_navigation)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchView(
                modifier = Modifier.fillMaxWidth(),
                searchQuery = uiState.searchQuery,
                onQueryChanged = {
                    onEvent(NoteSearchUIEvent.OnSearchQueryChanged(it))
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
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
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier
                            .testTag("NoteList")
                            .fillMaxSize(),
                        columns = StaggeredGridCells.Adaptive(300.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalItemSpacing = 16.dp

                    ) {
                        items(noteListResponse.data) {
                            NoteListItem(
                                onEvent = onEvent,
                                note = it
                            )
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
fun NoteListItem(
    note: NoteListResponseItem,
    onEvent: (NoteSearchUIEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onEvent(NoteSearchUIEvent.OnNoteClicked(note)) },
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
                    text = convertLongToDate(note.date),
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
private fun NoteSearchPreview() {
    val noteList = listOf(
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › " +
                    "kotlin-convert-t. I'm trying to find out how I can convert timestamp to " +
                    "datetime in Kotlin, this is very simple in Java but I cant find any " +
                    "equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        ),
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › " +
                    "kotlin-convert-t. I'm trying to find out how I can convert timestamp to " +
                    "datetime in Kotlin, this is very simple in Java but I cant find any " +
                    "equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        ),
        NoteListResponseItem(
            noteTitle = "Note Title",
            description = "Stack Overflow https://stackoverflow.com › questions › " +
                    "kotlin-convert-t. I'm trying to find out how I can convert timestamp to " +
                    "datetime in Kotlin, this is very simple in Java but I cant find any " +
                    "equivalent of it in Kotlin.",
            id = 1,
            date = 1740338779
        )
    )
    NoteList(
        uiState = NoteSearchUIState(),
        onEvent = {},
        noteListResponse = NoteSearchState(
            data = noteList
        )
    )
}