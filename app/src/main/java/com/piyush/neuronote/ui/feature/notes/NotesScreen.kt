package com.piyush.neuronote.ui.feature.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotesScreen(
    viewModel: NotesViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NotesEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    // UI
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onIntent(
                        NotesIntent.AddNote(
                            title = "New Note",
                            content = "Sample content"
                        )
                    )
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val s = state) {
                is NotesUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is NotesUiState.Error -> {
                    Text(text = s.message)
                }
                is NotesUiState.Success -> {
                    NotesList(
                        notes = s.notes,
                        onDelete = { id ->
                            viewModel.onIntent(
                                NotesIntent.DeleteNote(id)
                            )
                        }
                    )
                }
            }
        }
    }
}