package com.codeturtle.notes.notes.notes_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.preference.tokken.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.notes.notes_list.domain.model.NoteListResponseItem
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
) : ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val _noteListResponse = mutableStateOf(NoteListState())
    val noteListResponse: State<NoteListState> = _noteListResponse

    private val _searchIconEvent = Channel<SearchIconClickedEvent>()
    val searchIconEvent = _searchIconEvent.receiveAsFlow()

    private val _logoutIconEvent = Channel<LogoutIconClickedEvent>()
    val logoutIconEvent = _logoutIconEvent.receiveAsFlow()

    private val _addNoteEvent = Channel<AddNoteEvent>()
    val addNoteEvent = _addNoteEvent.receiveAsFlow()

    private val _noteDetailEvent = Channel<NoteClickEvent>()
    val noteDetailEvent = _noteDetailEvent.receiveAsFlow()

    init {
        getNoteList()
    }

    fun onEvent(uiEvent: NoteListUIEvent) {
        when (uiEvent) {
            NoteListUIEvent.OnSearchIconClicked -> {
                viewModelScope.launch {
                    _searchIconEvent.send(SearchIconClickedEvent.Callback)
                }
            }

            NoteListUIEvent.OnLogoutIconClicked -> {
                viewModelScope.launch {
                    _logoutIconEvent.send(LogoutIconClickedEvent.Callback)
                }
            }

            NoteListUIEvent.OnAddNoteClicked -> {
                viewModelScope.launch {
                    _addNoteEvent.send(AddNoteEvent.Callback)
                }
            }

            is NoteListUIEvent.OnNoteClicked -> {
                viewModelScope.launch {
                    _noteDetailEvent.send(NoteClickEvent.Callback(note = uiEvent.note))
                }
            }
        }
    }

    private fun getNoteList() = viewModelScope.launch {
        useCase().onEach {
            when (it) {
                is Resource.Loading -> _noteListResponse.value = NoteListState(isLoading = true)
                is Resource.DataError -> _noteListResponse.value =
                    NoteListState(dataError = it.errorData)

                is Resource.Error -> _noteListResponse.value =
                    NoteListState(errorMessage = it.errorMessage.toString())

                is Resource.Success -> _noteListResponse.value = NoteListState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

    sealed class SearchIconClickedEvent {
        data object Callback : SearchIconClickedEvent()
    }

    sealed class LogoutIconClickedEvent {
        data object Callback : LogoutIconClickedEvent()
    }

    sealed class AddNoteEvent {
        data object Callback : AddNoteEvent()
    }

    sealed class NoteClickEvent(
        val note: NoteListResponseItem? = null
    ) {
        class Callback(note:NoteListResponseItem?) : NoteClickEvent(note = note)
    }
}
