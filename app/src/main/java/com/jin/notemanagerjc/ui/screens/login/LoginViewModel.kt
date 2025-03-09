package com.jin.notemanagerjc.ui.screens.login

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

data class LoginUiState(
    val userName: String = "",
    val passWord: String = "",
    val state: LoadStatus = LoadStatus.Init(),
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val log: MainLog, private val api: Api
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUserName(userName: String) {
        _uiState.value = _uiState.value.copy(userName = userName)
    }

    fun updatePassword(passWord: String) {
        _uiState.value = _uiState.value.copy(passWord = passWord)
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(state = LoadStatus.Init())
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(state = LoadStatus.Loading())
            try {
                val result = api.login(uiState.value.userName, uiState.value.passWord)
                _uiState.value = _uiState.value.copy(state = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = LoadStatus.Error(e.message ?: ""))
            }
        }
    }

    fun log(tag: String, msg: String) {
        log.i(tag, msg)
    }
}