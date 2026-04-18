package com.piyush.neuronote.ui.feature.notes

import com.piyush.neuronote.domain.model.Note

sealed class NotesUiState {

    object Loading : NotesUiState()

    data class Success(
        val notes: List<Note>,
        val isUndoAvailable: Boolean = false
    ) : NotesUiState()

    data class Error(
        val message: String
    ) : NotesUiState()
}

sealed class NotesIntent {

    object LoadNotes : NotesIntent()

    data class AddNote(
        val title: String,
        val content: String
    ) : NotesIntent()

    data class DeleteNote(
        val noteId: Long
    ) : NotesIntent()

    object UndoDelete : NotesIntent()
}

sealed class NotesEffect {

    data class ShowSnackbar(
        val message: String
    ) : NotesEffect()
}