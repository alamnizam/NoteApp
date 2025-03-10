package com.codeturtle.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeturtle.notes.common.tokken.TokenManager
import com.codeturtle.notes.common.utils.Resource
import com.codeturtle.notes.domain.model.NoteListResponseItem
import com.codeturtle.notes.domain.usecase.NoteListUseCase
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
class NoteListViewModel @Inject constructor(
    private val useCase: NoteListUseCase
) : ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val _noteListResponse = MutableStateFlow(NoteListState())
    val noteListResponse: StateFlow<NoteListState> = _noteListResponse


    private val _searchIconClickedEvent = Channel<SearchIconClickedEvent>()
    val searchIconClickedEvent = _searchIconClickedEvent.receiveAsFlow()

    private val _logoutIconClickedEvent = Channel<LogoutIconClickedEvent>()
    val logoutIconClickedEvent = _logoutIconClickedEvent.receiveAsFlow()

    private val _addNoteClickedEvent = Channel<AddNoteClickedEvent>()
    val addNoteClickedEvent = _addNoteClickedEvent.receiveAsFlow()

    private val _noteClickedEvent = Channel<NoteClickEvent>()
    val noteClickedEvent = _noteClickedEvent.receiveAsFlow()

    private var isListLoaded = false

    init {
        getNoteList()
    }

    fun onEvent(uiEvent: NoteListUIEvent) {
        when (uiEvent) {
            NoteListUIEvent.OnSearchIconClicked -> {
                viewModelScope.launch {
                    _searchIconClickedEvent.send(SearchIconClickedEvent.Callback)
                }
            }

            NoteListUIEvent.OnLogoutIconClicked -> {
                viewModelScope.launch {
                    _logoutIconClickedEvent.send(LogoutIconClickedEvent.Callback)
                }
            }

            NoteListUIEvent.OnAddNoteClicked -> {
                viewModelScope.launch {
                    _addNoteClickedEvent.send(AddNoteClickedEvent.Callback)
                }
            }

            is NoteListUIEvent.OnNoteClicked -> {
                viewModelScope.launch {
                    _noteClickedEvent.send(NoteClickEvent.Callback(note = uiEvent.note))
                }
            }
        }
    }


    fun getNoteList() {
        if(isListLoaded) return
        viewModelScope.launch {
            useCase().onEach {
                when (it) {
                    is Resource.Loading -> _noteListResponse.value = NoteListState(isLoading = true)
                    is Resource.DataError -> _noteListResponse.value =
                        NoteListState(dataError = it.errorData)

                    is Resource.Error -> _noteListResponse.value =
                        NoteListState(errorMessage = it.errorMessage.toString())

                    is Resource.Success -> {
                        println("Notes loaded: ${it.data}")
                        _noteListResponse.value = NoteListState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    sealed class SearchIconClickedEvent {
        data object Callback : SearchIconClickedEvent()
    }

    sealed class LogoutIconClickedEvent {
        data object Callback : LogoutIconClickedEvent()
    }

    sealed class AddNoteClickedEvent {
        data object Callback : AddNoteClickedEvent()
    }

    sealed class NoteClickEvent(
        val note: NoteListResponseItem? = null
    ) {
        class Callback(note:NoteListResponseItem?) : NoteClickEvent(note = note)
    }
}
