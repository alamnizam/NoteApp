package com.codeturtle.notes.notes.notes_list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codeturtle.notes.R

@Composable
fun NoteListScreen(navController: NavHostController) {
    NoteList()
}

@Composable
fun NoteList() {
    Column(
        modifier = Modifier
            .testTag("NoteList")
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.note_list),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoteListScreenPreview() {
    NoteList()
}
