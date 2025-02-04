package com.codeturtle.notes.common.snakbar

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
)
