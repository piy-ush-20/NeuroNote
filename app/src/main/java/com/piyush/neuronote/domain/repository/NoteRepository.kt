package com.piyush.neuronote.domain.repository

import com.piyush.neuronote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(noteId: Long)
}