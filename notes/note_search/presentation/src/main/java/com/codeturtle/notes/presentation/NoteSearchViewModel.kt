package com.codeturtle.notes.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.domain.usecase.NoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteSearchViewModel @Inject constructor(
    private val useCase: NoteListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteSearchUIState())
    val uiState: StateFlow<NoteSearchUIState> = _uiState

    private val _noteListResponse = mutableStateOf(NoteSearchState())
    val noteListResponse: State<NoteSearchState> = _noteListResponse

    private val _originalNoteList = mutableStateOf<List<NoteListResponseItem>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery: StateFlow<String> = _searchQuery

    private val _backArrowIconClickedEvent = Channel<BackArrowClickedIconEvent>()
    val backArrowIconClickedEvent = _backArrowIconClickedEvent.receiveAsFlow()

    private val _noteClickedEvent = Channel<NoteClickEvent>()
    val noteClickedEvent = _noteClickedEvent.receiveAsFlow()

    init {
        getNoteList()
        observeSearchQuery()
    }

    fun onEvent(uiEvent: NoteSearchUIEvent) {
        when (uiEvent) {
            is NoteSearchUIEvent.OnNoteClicked -> {
                viewModelScope.launch {
                    _noteClickedEvent.send(NoteClickEvent.Callback(note = uiEvent.note))
                }
            }

            is NoteSearchUIEvent.OnSearchQueryChanged -> {
                _uiState.value = _uiState.value.copy(searchQuery = uiEvent.query)
                _searchQuery.value = uiEvent.query

            }

            NoteSearchUIEvent.OnBackNavigationClicked -> {
                viewModelScope.launch {
                    _backArrowIconClickedEvent.send(BackArrowClickedIconEvent.Callback)
                }
            }

            else -> {}
        }
    }

    private fun getNoteList() = viewModelScope.launch {
        useCase().onEach {
            when (it) {
                is Resource.Loading -> _noteListResponse.value = NoteSearchState(isLoading = true)
                is Resource.DataError -> _noteListResponse.value =
                    NoteSearchState(dataError = it.errorData)

                is Resource.Error -> _noteListResponse.value =
                    NoteSearchState(errorMessage = it.errorMessage.toString())

                is Resource.Success -> {
                    _originalNoteList.value = it.data ?: emptyList()
                    _noteListResponse.value = NoteSearchState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300L)
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    private fun performSearch(query: String) {
        val notes = _originalNoteList.value

        val filteredNotes = if (query.isNotBlank()) {
            notes.filter { it.isMatchWithQuery(query) }
        } else {
            notes
        }

        _noteListResponse.value = _noteListResponse.value.copy(data = filteredNotes)
    }

    sealed class BackArrowClickedIconEvent {
        data object Callback : BackArrowClickedIconEvent()
    }

    sealed class NoteClickEvent(
        val note: NoteListResponseItem? = null
    ) {
        class Callback(note:NoteListResponseItem?) : NoteClickEvent(note = note)
    }
}
