package com.piyush.neuronote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.piyush.neuronote.ui.feature.notes.NotesScreen
import com.piyush.neuronote.ui.feature.notes.NotesViewModel
import com.piyush.neuronote.ui.feature.notes.NotesIntent
import com.piyush.neuronote.domain.repository.NoteRepositoryImpl

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as NeuroNoteApp
        val dao = app.database.noteDao()
        val repository = NoteRepositoryImpl(dao)

        viewModel = NotesViewModel(repository)

        setContent {
            NotesScreen(viewModel)
        }

        // Initial load
        viewModel.onIntent(NotesIntent.LoadNotes)
    }
}