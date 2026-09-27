package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateNoteFields
import com.codeturtle.notes.domain.model.AddNoteRequest
import com.codeturtle.notes.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val validateNoteFields: ValidateNoteFields,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNoteUIState())
    val uiState: StateFlow<AddNoteUIState> = _uiState

    private val _addNoteResponse = mutableStateOf(AddNoteState())
    val addNoteResponse: State<AddNoteState> = _addNoteResponse

    private val _backArrowIconClickedEvent = Channel<BackArrowIconClickedEvent>()
    val backArrowIconClickedEvent = _backArrowIconClickedEvent.receiveAsFlow()

    fun onEvent(uiEvent: AddNoteUIEvent) {
        when (uiEvent) {
            AddNoteUIEvent.OnBackNavigationClicked -> {
                viewModelScope.launch {
                    _backArrowIconClickedEvent.send(BackArrowIconClickedEvent.Callback)
                }
            }

            is AddNoteUIEvent.OnTitleChanged -> _uiState.value =
                _uiState.value.copy(title = uiEvent.title)

            is AddNoteUIEvent.OnDescriptionChanged -> _uiState.value =
                _uiState.value.copy(description = uiEvent.description)

            AddNoteUIEvent.OnSaveNoteClicked -> saveNote()
        }
    }

    private fun saveNote() {
        val state = _uiState.value
        val validation = validateNoteFields.execute(state.title, state.description)
        _uiState.value = state.copy(
            titleError = validation.titleError,
            descriptionError = validation.descriptionError
        )

        if (validation.isValid) {
            val date = System.currentTimeMillis() / 1000
            val request = AddNoteRequest(
                date = date,
                noteTitle = state.title,
                description = state.description
            )
            addNote(request)
        }
    }

    private fun addNote(request: AddNoteRequest) = viewModelScope.launch {
        addNoteUseCase(request).collect { resource ->
            when (resource) {
                is Resource.Loading -> _addNoteResponse.value = AddNoteState(isLoading = true)
                is Resource.Error -> _addNoteResponse.value =
                    AddNoteState(errorMessage = resource.errorMessage.toString())
                is Resource.DataError -> _addNoteResponse.value =
                    AddNoteState(errorData = resource.errorData)
                is Resource.Success -> _addNoteResponse.value = AddNoteState(data = resource.data)
            }
        }
    }

    sealed class BackArrowIconClickedEvent {
        data object Callback : BackArrowIconClickedEvent()
    }
}
