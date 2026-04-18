package com.piyush.neuronote.ui.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

import com.piyush.neuronote.core.util.Logger
import com.piyush.neuronote.domain.model.Note
import com.piyush.neuronote.domain.repository.NoteRepository

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    // State
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    // Effect
    private val _effect = Channel<NotesEffect>(Channel.BUFFERED)
    val effect: Flow<NotesEffect> = _effect.receiveAsFlow()

    // Concurrency Control
    private val mutex = Mutex()

    // Intent Entry Point
    fun onIntent(intent: NotesIntent) {
        viewModelScope.launch {
            mutex.withLock {
                val oldState = _uiState.value
                Logger.d("Intent: $intent | State: $oldState")

                processIntent(intent, oldState)
            }
        }
    }

    // Intent Processor
    private suspend fun processIntent(
        intent: NotesIntent,
        currentState: NotesUiState
    ) {
        when (intent) {
            is NotesIntent.LoadNotes -> observeNotes()
            is NotesIntent.AddNote -> addNote(intent)
            is NotesIntent.DeleteNote -> deleteNote(intent, currentState)
            is NotesIntent.UndoDelete -> undoDelete(currentState)
        }
    }

    // Data Stream
    private fun observeNotes() {
        viewModelScope.launch {
            repository.observeNotes()
                .onStart {
                    _uiState.value = NotesUiState.Loading
                }
                .catch { e ->
                    _uiState.value = NotesUiState.Error(e.message ?: "Unknown Error")
                }
                .collect { notes ->
                    _uiState.value = NotesUiState.Success(notes)
                }

        }
    }

    // Add Note
    private suspend fun addNote(intent: NotesIntent.AddNote) {
        val note = Note(
            id = 0,
            title = intent.title,
            content = intent.content,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        repository.addNote(note)

        _effect.send(NotesEffect.ShowSnackbar("Note added"))
    }

    // Delete Note (Soft Delete)
    private suspend fun deleteNote(
        intent: NotesIntent.DeleteNote,
        currentState: NotesUiState
    ) {
        repository.deleteNote(intent.noteId)

        if (currentState is NotesUiState.Success) {
            _uiState.value = currentState.copy(isUndoAvailable = true)
        }

        _effect.send(NotesEffect.ShowSnackbar("Note deleted"))
    }
    private suspend fun undoDelete(currentState: NotesUiState) {
        // We'll implement actual undo logic later

        if (currentState is NotesUiState.Success) {
            _uiState.value = currentState.copy(isUndoAvailable = false)
        }

        _effect.send(NotesEffect.ShowSnackbar("Undo not implemented yet"))
    }
}