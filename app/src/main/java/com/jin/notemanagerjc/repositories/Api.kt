package com.jin.notemanagerjc.repositories

import com.jin.notemanagerjc.model.NoteItem

interface Api {
    suspend fun login(userName: String, password: String): Boolean
    suspend fun loadNotes(): List<NoteItem>
    suspend fun addNote(title: String, content: String)
    suspend fun editNote(dt: Long, title: String, content: String)
    suspend fun deleteNote(dt: Long)
}