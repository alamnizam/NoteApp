package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateNoteFields
import com.codeturtle.notes.domain.model.EditNoteRequest
import com.codeturtle.notes.domain.usecase.EditNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val validateNoteFields: ValidateNoteFields,
    private val editNoteUseCase: EditNoteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditNoteUIState())
    val uiState: StateFlow<EditNoteUIState> = _uiState

    private val _editNoteResponse = mutableStateOf(EditNoteState())
    val editNoteResponse: State<EditNoteState> = _editNoteResponse

    private val _backArrowIconEvent = Channel<BackArrowIconEvent>()
    val backArrowIconEvent = _backArrowIconEvent.receiveAsFlow()

    fun onEvent(uiEvent: EditNoteUIEvent) {
        when (uiEvent) {
            is EditNoteUIEvent.OnTitleChanged -> {
                _uiState.value = _uiState.value.copy(title = uiEvent.title)
            }
            is EditNoteUIEvent.OnDescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = uiEvent.description)
            }
            is EditNoteUIEvent.SetHiddenId -> {
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
        val state = _uiState.value
        val validation = validateNoteFields.execute(state.title, state.description)
        _uiState.value = state.copy(
            titleError = validation.titleError,
            descriptionError = validation.descriptionError
        )

        if (validation.isValid) {
            val date = System.currentTimeMillis() / 1000
            val request = EditNoteRequest(
                id = state.id,
                date = date,
                noteTitle = state.title,
                description = state.description
            )
            updateNote(request)
        }
    }

    private fun updateNote(request: EditNoteRequest) = viewModelScope.launch {
        editNoteUseCase(request).collect { resource ->
            when (resource) {
                is Resource.Loading -> _editNoteResponse.value = EditNoteState(isLoading = true)
                is Resource.Error -> _editNoteResponse.value =
                    EditNoteState(errorMessage = resource.errorMessage.toString())
                is Resource.DataError -> _editNoteResponse.value =
                    EditNoteState(errorData = resource.errorData)
                is Resource.Success -> _editNoteResponse.value = EditNoteState(data = resource.data)
            }
        }
    }

    sealed class BackArrowIconEvent {
        data object Callback : BackArrowIconEvent()
    }

}
