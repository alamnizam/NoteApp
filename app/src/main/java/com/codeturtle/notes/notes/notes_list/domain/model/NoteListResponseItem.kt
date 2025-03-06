package com.codeturtle.notes.notes.notes_list.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NoteListResponseItem(
    val date: Long,
    val description: String,
    val id: Int,
    val noteTitle: String
): Parcelable{
    fun isMatchWithQuery(queryString: String): Boolean {
        val matchResult = listOf(
            noteTitle, "${noteTitle.first()}"
        )

        return matchResult.any {
            it.contains(queryString, true)
        }
    }
}