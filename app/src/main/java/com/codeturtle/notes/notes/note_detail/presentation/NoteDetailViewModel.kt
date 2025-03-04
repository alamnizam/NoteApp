package com.codeturtle.notes.notes.note_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor() : ViewModel() {
    private val _backArrowIconEvent = Channel<BackArrowIconEvent>()
    val backArrowIconEvent = _backArrowIconEvent.receiveAsFlow()

    private val _editIconEvent = Channel<EditIconEvent>()
    val editIconEvent = _editIconEvent.receiveAsFlow()

    fun onEvent(uiEvent: NoteDetailUIEvent) {
        when(uiEvent){
            NoteDetailUIEvent.OnBackNavigationClicked -> {
                viewModelScope.launch {
                    _backArrowIconEvent.send(BackArrowIconEvent.Callback)
                }
            }
            is NoteDetailUIEvent.OnEditNoteClicked -> {
                viewModelScope.launch {
                    _editIconEvent.send(EditIconEvent.Callback(uiEvent.note))
                }
            }
        }
    }

    sealed class BackArrowIconEvent {
        data object Callback : BackArrowIconEvent()
    }

    sealed class EditIconEvent(
        val note: NoteListResponseItem? = null
    ) {
        class Callback(note:NoteListResponseItem?) : EditIconEvent(note = note)
    }
}
