package com.jin.notemanagerjc.common.enumz

sealed class LoadStatus(val description: String) {
    class Init : LoadStatus("Init")
    class Loading : LoadStatus("Loading")
    class Success : LoadStatus("Success")
    class Error(val msg: String) : LoadStatus(msg)
}