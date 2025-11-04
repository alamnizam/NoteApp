package com.codeturtle.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.codeturtle.notes.common.R
import com.codeturtle.notes.common.component.AlertDialog
import com.codeturtle.notes.common.component.ProgressBar
import com.codeturtle.notes.common.snakbar.SnackBarController
import com.codeturtle.notes.common.snakbar.SnackBarEvent
import com.codeturtle.notes.common.utils.HandleDate.convertLongToDate
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.navigation.EditNoteScreen
import com.codeturtle.notes.navigation.NoteDetailScreen
import com.codeturtle.notes.navigation.NoteListScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NoteDetailScreen(
    navController: NavHostController,
    viewModel: NoteDetailViewModel = hiltViewModel<NoteDetailViewModel>(),
    note: NoteDetailScreen
) {
    val deleteNoteResponse = viewModel.deleteNoteResponse.value
    val deleteIconEvent = viewModel.deleteIconEvent.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    deleteIconEvent.value.let {
        if(deleteNoteResponse.isLoading){
            ProgressBar()
        }
        if (deleteNoteResponse.errorMessage.isNotBlank()) {
            scope.launch {
                SnackBarController.sendEvent(
                    event = SnackBarEvent(
                        message = deleteNoteResponse.errorMessage
                    )
                )
            }
        }
        if (deleteNoteResponse.data != null) {
            scope.launch {
                val job = launch {
                    SnackBarController.sendEvent(
                        event = SnackBarEvent(
                            message = context.getString(R.string.note_deleted_successfully)
                        )
                    )
                }
                job.join()
                withContext(Dispatchers.Main) {
                    navController.popBackStack(
                        route = NoteListScreen,
                        inclusive = false
                    )
                }
            }
        }
        if (deleteNoteResponse.errorData != null) {
            scope.launch {
                SnackBarController.sendEvent(
                    event = SnackBarEvent(
                        message = deleteNoteResponse.errorData.message
                    )
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.backArrowIconEvent.collect{
            navController.popBackStack()
        }
    }

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
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    note: NoteDetailScreen,
    onEvent: (NoteDetailUIEvent) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    if(showDialog){
        AlertDialog(
            heading = stringResource(R.string.are_you_sure_you_want_to_delete_this_note),
            onYesClicked = {
                onEvent(NoteDetailUIEvent.OnDeleteNoteClicked(note.note.id))
            },
            onNoClicked = { showDialog = false }
        )
    }
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
                            contentDescription = stringResource(R.string.edit_icon)
                        )
                    }

                    IconButton(
                        modifier = Modifier.testTag("DeleteNote"),
                        onClick = { showDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete_icon)
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeContent, modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
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
        onEvent = {}
    )
}
