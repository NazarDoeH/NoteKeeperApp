package com.lab5.ui.screens.noteEditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.backend.TagsManager
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.entity.TagsEntity
import com.lab5.ui.elements.TagsSection
import com.lab5.ui.theme.BackgroundMain
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.TextMain
import com.lab5.ui.theme.TextSecondary
import java.sql.Date
import org.koin.androidx.compose.getViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun NoteEditorScreen(
    noteId: Int,  // Для нової нотатки передається 0 або інше значення
    onCancel: () -> Unit,
    onSave: () -> Unit,
    viewModel: NoteEditorViewModel = getViewModel(),
    tagsManager: TagsManager // Додано передачу tagsManager
) {
    var note by remember { mutableStateOf<NotesEntity?>(null) }
    val tags by viewModel.tagsStateFlow.collectAsState() // Теги, які доступні для вибору

    // Якщо noteId == 0, то це створення нової нотатки
    if (noteId != 0) {
        // Завантаження існуючої нотатки для редагування
        LaunchedEffect(noteId) {
            viewModel.getNoteById(noteId) {
                note = it
            }
        }
    } else {
        // Ініціалізація порожньої нотатки для створення нової
        note = NotesEntity(title = "", text = "", editTime = Date(System.currentTimeMillis()))
    }

    // Якщо нотатка доступна (або нова нотатка ініціалізована)
    if (note != null) {
        val currentNote = note!!
        var title by remember { mutableStateOf(currentNote.title) }
        var text by remember { mutableStateOf(currentNote.text) }
        val appliedTags = remember { mutableStateListOf<TagsEntity>() } // Список вибраних тегів

        LaunchedEffect(currentNote.id) {
            tagsManager.getTagsForNoteFlow(currentNote.id).collect { tags ->
                appliedTags.clear() // Clear previous tags
                appliedTags.addAll(tags) // Add the new tags
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundMain)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.clickable(onClick = {
                            val updatedNote = currentNote.copy(title = title, text = text, editTime = Date(System.currentTimeMillis()))
                            if (noteId != 0) {
                                viewModel.updateNote(updatedNote) {
                                    onSave()
                                }
                            } else {
                                viewModel.insertNote(updatedNote) {
                                    onSave()
                                }
                            }
                        })
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextMain
                        )
                    }
                }

                BasicTextField(
                    value = title,
                    textStyle = LocalTextStyle.current.copy(color = TextMain, fontSize = 25.sp, fontFamily = JetBrainsMono, fontWeight = FontWeight.Bold),
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                Text(
                    text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(currentNote.editTime.toInstant()),
                    fontFamily = JetBrainsMono,
                    color = TextSecondary,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Tags Section
                TagsSection(
                    tags = tags,
                    appliedTags = appliedTags,
                    onTagSelected = { tag ->
                        if (appliedTags.contains(tag)) {
                            viewModel.deleteTagFromNote(currentNote, tag)
                            appliedTags.remove(tag) // Remove tag
                        } else {
                            viewModel.addTagToNote(currentNote, tag)
                            appliedTags.add(tag) // Add tag
                        }
                    }
                )
                BasicTextField(
                    value = text,
                    textStyle = LocalTextStyle.current.copy(color = TextMain, fontFamily = JetBrainsMono),
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp)
                        .padding(8.dp)
                        .imePadding()
                )
            }
        }
    } else {
        // Індикація завантаження
        Text("Loading...", modifier = Modifier.fillMaxSize())
    }
}