package com.codeturtle.notes.common.snakbar

data class SnackBarAction(
    val name: String,
    val action: suspend () -> Unit
)
