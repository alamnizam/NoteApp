package com.codeturtle.notes.notes.notes_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.preference.tokken.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.notes.notes_list.domain.usecase.NoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCase: NoteListUseCase
): ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val _noteListResponse = mutableStateOf(NoteListState())
    val noteListResponse: State<NoteListState> = _noteListResponse

    private val _searchIconEvent = Channel<SearchIconEvent>()
    val searchIconEvent = _searchIconEvent.receiveAsFlow()

    private val _logoutIconEvent = Channel<LogoutIconEvent>()
    val logoutIconEvent = _logoutIconEvent.receiveAsFlow()

    private val _addNoteEvent = Channel<AddNoteEvent>()
    val addNoteEvent = _addNoteEvent.receiveAsFlow()

    init {
        getNoteList()
    }

    fun onEvent(uiEvent: NoteListUIEvent) {
        when (uiEvent) {
            NoteListUIEvent.SearchIconClicked -> {
                viewModelScope.launch {
                    _searchIconEvent.send(SearchIconEvent.Callback)
                }
            }
            NoteListUIEvent.LogoutIconClicked -> {
                viewModelScope.launch {
                    _logoutIconEvent.send(LogoutIconEvent.Callback)
                }
            }
            NoteListUIEvent.AddNoteClicked -> {
                viewModelScope.launch {
                    _addNoteEvent.send(AddNoteEvent.Callback)
                }
            }
        }
    }

    private fun getNoteList() = viewModelScope.launch {
        useCase().onEach {
            when(it){
                is Resource.Loading -> _noteListResponse.value = NoteListState(isLoading = true)
                is Resource.DataError -> _noteListResponse.value = NoteListState(dataError = it.errorData)
                is Resource.Error -> _noteListResponse.value = NoteListState(errorMessage = it.errorMessage.toString())
                is Resource.Success -> _noteListResponse.value = NoteListState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

    sealed class SearchIconEvent {
        data object Callback : SearchIconEvent()
    }
    sealed class LogoutIconEvent {
        data object Callback : LogoutIconEvent()
    }
    sealed class AddNoteEvent {
        data object Callback : AddNoteEvent()
    }
}
