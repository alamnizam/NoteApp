package com.codeturtle.notes.common.snackbar

data class SnackBarAction(
    val name: String,
    val action: suspend () -> Unit
)
