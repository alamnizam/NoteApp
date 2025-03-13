package com.codeturtle.notes.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeturtle.notes.common.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onYesClicked: () -> Unit = {},
    onNoClicked: () -> Unit = {},
    heading: String
) {
    BasicAlertDialog(
        onDismissRequest = { onNoClicked() }
    ) {
        Card(
            modifier = Modifier.padding(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = heading,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onNoClicked() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF339933)
                        )
                    ) {
                        Text(text = "No")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { onYesClicked() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(text = "Yes")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AlertDialogPreview() {
    AlertDialog(
        heading = stringResource(R.string.are_you_sure_you_want_to_delete_this_note)
    )
}