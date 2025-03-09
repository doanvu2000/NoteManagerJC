package com.jin.notemanagerjc.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.notemanagerjc.common.enumz.LoadStatus
import com.jin.notemanagerjc.model.NoteItem
import com.jin.notemanagerjc.repositories.Api
import com.jin.notemanagerjc.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val notes: List<NoteItem> = emptyList(),
    val status: LoadStatus = LoadStatus.Init(),
    val selectedIndex: Int = -1
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog, private val api: Api
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val loadNotes = api.loadNotes()
                _uiState.value =
                    _uiState.value.copy(notes = loadNotes, status = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: ""))
            }
        }
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())
    }

    fun deleteNote(dt: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                api.deleteNote(dt)
                val loadNotes = api.loadNotes()
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Success(), notes = loadNotes)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: ""))
            }
        }
    }

    fun selectNote(index: Int) {
        _uiState.value = _uiState.value.copy(selectedIndex = index)
    }
}