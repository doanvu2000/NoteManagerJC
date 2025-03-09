package com.jin.notemanagerjc.ui.screens.addoredit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.notemanagerjc.common.enumz.LoadStatus
import com.jin.notemanagerjc.repositories.Api
import com.jin.notemanagerjc.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddOrEditUiState(
    val dateTime: Long = -1,
    val title: String = "",
    val content: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val log: MainLog, private val api: Api
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddOrEditUiState())
    val uiState = _uiState.asStateFlow()

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())

            try {
                api.addNote(title, content)
                _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: ""))
            }
        }
    }

    fun editNote(dt: Long, title: String, content: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())

            try {
                api.editNote(dt, title, content)
                _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: ""))
            }
        }
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())
    }
}