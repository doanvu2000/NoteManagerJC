package com.jin.notemanagerjc.repositories

import com.jin.notemanagerjc.model.NoteItem
import kotlinx.coroutines.delay
import javax.inject.Inject

class ApiImpl @Inject constructor() : Api {

    var notes = ArrayList<NoteItem>()

    init {
        notes.add(NoteItem(System.currentTimeMillis(), "Title 1", "Content 1"))
        notes.add(NoteItem(System.currentTimeMillis(), "Title 2", "Content 2"))
        notes.add(NoteItem(System.currentTimeMillis(), "Title 3", "Content 3"))
    }

    override suspend fun login(userName: String, password: String): Boolean {
        delay(1500)
        if (userName != "1" && password != "1") {
            throw Exception("Wrong credentials")
        }
        return true
    }

    override suspend fun loadNotes(): List<NoteItem> {
        delay(1000)
        return notes
    }

    override suspend fun addNote(title: String, content: String) {
        delay(200)
        notes.add(NoteItem(System.currentTimeMillis(), title, content))
    }

    override suspend fun editNote(dt: Long, title: String, content: String) {
        delay(500)
        notes.forEach {
            if (it.dateTime == dt) {
                it.title = title
                it.content = content
            }
        }
    }

    override suspend fun deleteNote(dt: Long) {
        delay(500)
        for (i in notes.indices) {
            if (notes[i].dateTime == dt) {
                notes.removeAt(i)
                break
            }
        }
    }
}