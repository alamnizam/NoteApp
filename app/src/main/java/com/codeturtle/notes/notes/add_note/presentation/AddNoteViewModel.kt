package com.codeturtle.notes.notes.add_note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.common.validation.ValidateFieldNotEmpty
import com.codeturtle.notes.notes.add_note.data.model.AddNoteRequest
import com.codeturtle.notes.notes.add_note.domain.usecase.AddNoteUseCase
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
class AddNoteViewModel @Inject constructor(
    private val validateFieldNotEmpty: ValidateFieldNotEmpty,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNoteUIState())
    val uiState: StateFlow<AddNoteUIState> = _uiState

    private val _addNoteResponse = mutableStateOf(AddNoteState())
    val addNoteResponse: State<AddNoteState> = _addNoteResponse

    private val _backArrowIconEvent = Channel<BackArrowIconEvent>()
    val backArrowIconEvent = _backArrowIconEvent.receiveAsFlow()

    private val _responseEvent = Channel<ResponseEvent>()
    val responseEvent = _responseEvent.receiveAsFlow()

    fun onEvent(uiEvent: AddNoteUIEvent) {
        when (uiEvent) {
            AddNoteUIEvent.OnBackNavigation -> {
                viewModelScope.launch {
                    _backArrowIconEvent.send(BackArrowIconEvent.Callback)
                }
            }

            is AddNoteUIEvent.OnTitleChanged -> _uiState.value =
                _uiState.value.copy(title = uiEvent.title)

            is AddNoteUIEvent.OnDescriptionChanged -> _uiState.value =
                _uiState.value.copy(description = uiEvent.description)

            AddNoteUIEvent.OnSaveNote -> saveNote()
        }
    }

    private fun saveNote() {
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
            val request = AddNoteRequest(
                date = date,
                noteTitle = _uiState.value.title,
                description = _uiState.value.description
            )
            addNote(request)
        }
        viewModelScope.launch {
            _responseEvent.send(ResponseEvent.Callback)
        }
    }

    private fun addNote(request: AddNoteRequest) = viewModelScope.launch {
        addNoteUseCase(request).onEach {
            when (it) {
                is Resource.Loading -> _addNoteResponse.value = AddNoteState(isLoading = true)
                is Resource.Error -> _addNoteResponse.value =
                    AddNoteState(errorMessage = it.errorMessage.toString())

                is Resource.DataError -> _addNoteResponse.value =
                    AddNoteState(errorData = it.errorData)

                is Resource.Success -> _addNoteResponse.value = AddNoteState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

    sealed class ResponseEvent {
        data object Callback : ResponseEvent()
    }

    sealed class BackArrowIconEvent {
        data object Callback : BackArrowIconEvent()
    }
}