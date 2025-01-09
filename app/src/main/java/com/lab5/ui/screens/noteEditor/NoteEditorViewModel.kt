package com.lab5.ui.screens.noteEditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.backend.TagsManager
import com.lab5.data.database.dao.TagsNotesDao
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.database.entity.TagsEntity
import com.lab5.data.database.entity.TagsNotesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date

class NoteEditorViewModel(
    private val database: NotekeeperDatabase,
    private val tagsManager: TagsManager
) : ViewModel() {

    private val _tagsStateFlow = MutableStateFlow<List<TagsEntity>>(emptyList())
    val tagsStateFlow: StateFlow<List<TagsEntity>> get() = _tagsStateFlow

    init {
        // Launching a coroutine inside ViewModel scope to call suspending function
        viewModelScope.launch {
            tagsManager.getTagsForNoteFlow(1) // Calling suspending function
        }
    }

    // Function to load a note by id
    fun getNoteById(id: Int, onResult: (NotesEntity?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Perform database query on the IO dispatcher
            val note = database.noteDao.getNoteById(id)

            // Ensure UI updates are done on the main thread
            withContext(Dispatchers.Main) {
                onResult(note)  // Update the UI
            }
        }
    }

    // Function to insert a new note
    fun insertNote(note: NotesEntity, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Perform database insertion on the IO dispatcher
            database.noteDao.insertNote(note)
            // Ensure UI updates are done on the main thread
            withContext(Dispatchers.Main) {
                onComplete()  // Notify that the insertion is complete
            }
        }
    }

    // Function to update an existing note
    fun updateNote(note: NotesEntity, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Perform database update on the IO dispatcher
            database.noteDao.update(note)

            // Ensure UI updates are done on the main thread
            withContext(Dispatchers.Main) {
                onComplete()  // Notify that the update is complete
            }
        }
    }
}
