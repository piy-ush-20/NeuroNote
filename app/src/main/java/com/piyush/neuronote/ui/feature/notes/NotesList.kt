package com.piyush.neuronote.ui.feature.notes

import com.piyush.neuronote.domain.model.Note


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotesList(
    notes: List<Note>,
    onDelete: (Long) -> Unit
) {
    LazyColumn {
        items(notes) { note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = note.content)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onDelete(note.id) }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}