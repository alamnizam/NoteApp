package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.domain.usecase.DeleteNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val useCase: DeleteNoteUseCase
) : ViewModel() {
    private val _deleteNoteResponse = mutableStateOf(DeleteNoteState())
    val deleteNoteResponse: State<DeleteNoteState> = _deleteNoteResponse

    private val _backArrowIconEvent = Channel<BackArrowIconEvent>()
    val backArrowIconEvent = _backArrowIconEvent.receiveAsFlow()

    private val _editIconEvent = Channel<EditIconEvent>()
    val editIconEvent = _editIconEvent.receiveAsFlow()

    fun onEvent(uiEvent: NoteDetailUIEvent) {
        when (uiEvent) {
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

            is NoteDetailUIEvent.OnDeleteNoteClicked -> {
                deleteNote(uiEvent.id)
            }
        }
    }

    private fun deleteNote(id: Int?) = viewModelScope.launch {
        id?.let {
            useCase(it).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _deleteNoteResponse.value =
                        DeleteNoteState(isLoading = true)
                    is Resource.Error -> _deleteNoteResponse.value =
                        DeleteNoteState(errorMessage = resource.errorMessage.toString())
                    is Resource.DataError -> _deleteNoteResponse.value =
                        DeleteNoteState(errorData = resource.errorData)
                    is Resource.Success -> _deleteNoteResponse.value =
                        DeleteNoteState(data = resource.data)
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
        class Callback(note: NoteListResponseItem?) : EditIconEvent(note = note)
    }
}
