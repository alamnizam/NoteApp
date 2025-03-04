package com.codeturtle.notes.notes.note_detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.codeturtle.notes.R
import com.codeturtle.notes.common.utils.HandleDate.convertLongToDate
import com.codeturtle.notes.notes.navigation.EditNoteScreen
import com.codeturtle.notes.notes.navigation.NoteDetailScreen
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem

@Composable
fun NoteDetailScreen(
    navController: NavHostController,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    note: NoteDetailScreen
) {
    LaunchedEffect(key1 = true) {
        viewModel.editIconEvent.collect{
            it.note?.let { note ->
                navController.navigate(EditNoteScreen(note = note))
            }
        }
    }
    NoteDetail(
        note = note,
        onEvent = {
            viewModel.onEvent(it)
        },
        snackBarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    note: NoteDetailScreen,
    onEvent: (NoteDetailUIEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.note_details),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag("BackNavigation"),
                        onClick = { onEvent(NoteDetailUIEvent.OnBackNavigationClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_navigation)
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag("EditNote"),
                        onClick = { onEvent(NoteDetailUIEvent.OnEditNoteClicked(note.note)) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.search_icon)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        contentWindowInsets = WindowInsets.safeContent, modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Note(note = note)
        }
    }
}

@Composable
private fun Note(note: NoteDetailScreen) {
    Text(
        text = note.note.noteTitle,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)
            ) {
                append(stringResource(R.string.published_on))
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight(500)
                )
            ) {
                append(convertLongToDate(note.note.date))
            }
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = note.note.description,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
}

@Preview
@Composable
private fun NoteDetailPreview() {
    NoteDetail(
        note = NoteDetailScreen(
            note = NoteListResponseItem(
                id = 1,
                noteTitle = "Test",
                description = "An examination (exam or evaluation) or test is an educational assessment intended to measure a test-taker's knowledge, skill, aptitude, physical fitness, or classification in many other topics (e.g., beliefs).[1] A test may be administered verbally, on paper, on a computer, or in a predetermined area that requires a test taker to demonstrate or perform a set of skills.\n" +
                        "\n" +
                        "Tests vary in style, rigor and requirements. There is no general consensus or invariable standard for test formats and difficulty. Often, the format and difficulty of the test is dependent upon the educational philosophy of the instructor, subject matter, class size, policy of the educational institution, and requirements of accreditation or governing bodies.\n" +
                        "\n" +
                        "A test may be administered formally or informally. An example of an informal test is a reading test administered by a parent to a child. A formal test might be a final examination administered by a teacher in a classroom or an IQ test administered by a psychologist in a clinic. Formal testing often results in a grade or a test score.[2] A test score may be interpreted with regards to a norm or criterion, or occasionally both. The norm may be established independently, or by statistical analysis of a large number of participants.\n" +
                        "\n" +
                        "A test may be developed and administered by an instructor, a clinician, a governing body, or a test provider. In some instances, the developer of the test may not be directly responsible for its administration. For example, in the United States, Educational Testing Service (ETS), a nonprofit educational testing and assessment organization, develops standardized tests such as the SAT but may not directly be involved in the administration or proctoring of these tests.",
                date = 1740338779
            )
        ),
        onEvent = {},
        snackBarHostState = SnackbarHostState()
    )
}
