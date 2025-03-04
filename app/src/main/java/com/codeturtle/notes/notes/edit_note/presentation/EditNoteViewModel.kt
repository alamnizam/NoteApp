package com.codeturtle.notes.notes.edit_note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateFieldNotEmpty
import com.codeturtle.notes.notes.edit_note.data.model.EditNoteRequest
import com.codeturtle.notes.notes.edit_note.domain.usecase.EditNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val validateFieldNotEmpty: ValidateFieldNotEmpty,
    private val editNoteUseCase: EditNoteUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(EditNoteUIState())
    val uiState: StateFlow<EditNoteUIState> = _uiState

    private val _editNoteResponse = mutableStateOf(EditNoteState())
    val editNoteResponse: State<EditNoteState> = _editNoteResponse

    private val _backArrowIconEvent = Channel<BackArrowIconEvent>()
    val backArrowIconEvent = _backArrowIconEvent.receiveAsFlow()

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent = _responseEvent.receiveAsFlow()

    fun onEvent(uiEvent: EditNoteUIEvent) {
        when(uiEvent){
            is EditNoteUIEvent.OnTitleChanged -> {
                _uiState.value = _uiState.value.copy(title = uiEvent.title)
            }
            is EditNoteUIEvent.OnDescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = uiEvent.description)
            }
            is EditNoteUIEvent.SetHiddenId ->{
                _uiState.value = _uiState.value.copy(id = uiEvent.id)
            }
            EditNoteUIEvent.OnBackNavigationClicked -> {
                viewModelScope.launch {
                    _backArrowIconEvent.send(BackArrowIconEvent.Callback)
                }
            }
            EditNoteUIEvent.OnEditNoteClicked -> editNote()
        }
    }

    private fun editNote() {
        val titleResult = validateFieldNotEmpty.execute(_uiState.value.title)
        val descriptionResult = validateFieldNotEmpty.execute(_uiState.value.description)
        val hasError = listOf(
            titleResult, descriptionResult
        ).any { !it.success }
        if (hasError) {
            _uiState.value = _uiState.value.copy(
                titleError = titleResult.errorMessage,
                descriptionError = descriptionResult.errorMessage
            )
        } else {
            _uiState.value = _uiState.value.copy(
                titleError = null,
                descriptionError = null
            )
        }
        if (!hasError) {
            val date = System.currentTimeMillis() / 1000
            val request = EditNoteRequest(
                id = _uiState.value.id,
                date = date,
                noteTitle = _uiState.value.title,
                description = _uiState.value.description
            )
            updateNote(request)
        }
        viewModelScope.launch {
            _responseEvent.send(ResponseEvent.Callback)
        }
    }

    private fun updateNote(request: EditNoteRequest) = viewModelScope.launch {
        editNoteUseCase(request).onEach {
            when(it){
                is Resource.Loading -> _editNoteResponse.value = EditNoteState(isLoading = true)
                is Resource.Error -> _editNoteResponse.value = EditNoteState(errorMessage = it.errorMessage.toString())
                is Resource.DataError -> _editNoteResponse.value = EditNoteState(errorData = it.errorData)
                is Resource.Success -> _editNoteResponse.value = EditNoteState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

    sealed class BackArrowIconEvent {
        data object Callback : BackArrowIconEvent()
    }

    sealed class ResponseEvent {
        data object Callback : ResponseEvent()
    }
}
