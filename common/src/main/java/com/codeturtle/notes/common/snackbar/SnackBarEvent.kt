package com.codeturtle.notes.common.snackbar

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
)
