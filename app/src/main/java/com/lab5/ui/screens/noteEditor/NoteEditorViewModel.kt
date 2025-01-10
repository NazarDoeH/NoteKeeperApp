package com.lab5.ui.screens.noteEditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.backend.TagsManager
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.database.entity.TagsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteEditorViewModel(
    private val database: NotekeeperDatabase,
    private val tagsManager: TagsManager
) : ViewModel() {

    private val _tagsStateFlow = MutableStateFlow<List<TagsEntity>>(emptyList())
    val tagsStateFlow: StateFlow<List<TagsEntity>> get() = _tagsStateFlow

    init {
        viewModelScope.launch {
            try {
                tagsManager.getAllTagsFlow().collect { tags ->
                    _tagsStateFlow.value = tags
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getNoteById(id: Int, onResult: (NotesEntity?) -> Unit) {
        viewModelScope.launch {
            try {
                val note = withContext(Dispatchers.IO) {
                    database.noteDao.getNoteById(id)
                }
                onResult(note)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }
    fun addTagToNote(currentNote: NotesEntity, tag: TagsEntity)
    {
        viewModelScope.launch {
            tagsManager.addTagForNote(currentNote.id, tag.id)
        }
    }
    fun deleteTagFromNote(currentNote: NotesEntity, tag: TagsEntity)
    {
        viewModelScope.launch {
            tagsManager.removeTagFromNote(currentNote.id, tag.id)
        }
    }

    fun insertNote(note: NotesEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.noteDao.insertNote(note)
                }
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateNote(note: NotesEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.noteDao.update(note)
                    database.noteTagDao.update(note)
                }
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

