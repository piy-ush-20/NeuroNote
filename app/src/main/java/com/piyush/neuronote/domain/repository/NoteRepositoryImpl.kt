package com.piyush.neuronote.domain.repository

import com.piyush.neuronote.data.local.NoteDao
import com.piyush.neuronote.data.local.toDomain
import com.piyush.neuronote.data.local.toEntity
import com.piyush.neuronote.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun observeNotes(): Flow<List<Note>> {
        return dao.observeNotes().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(noteId: Long) {
        dao.softDelete(noteId)
    }

}