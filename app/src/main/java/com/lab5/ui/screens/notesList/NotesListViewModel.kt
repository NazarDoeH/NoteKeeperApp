package com.lab5.ui.screens.notesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.backend.AlertManager
import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.entity.TagsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesListViewModel(
    val database: NotekeeperDatabase,
    val alertManager: AlertManager
) : ViewModel() {

    private val _regionUidStateFlow = MutableStateFlow<Int?>(null)
    val regionUidStateFlow: StateFlow<Int?>
        get() = _regionUidStateFlow

    private val _regionStateFlow = MutableStateFlow<String?>(null)
    val regionState: StateFlow<String?>
        get() = _regionStateFlow

    private val _notesStateFlow = MutableStateFlow<List<NotesEntity>>(emptyList())
    val notesStateFlow: StateFlow<List<NotesEntity>> get() = _notesStateFlow

    private val _tagsStateFlow = MutableStateFlow<List<TagsEntity>>(emptyList())
    val tagsStateFlow: StateFlow<List<TagsEntity>> get() = _tagsStateFlow

    private val _selectedTagsStateFlow = MutableStateFlow<Set<Int>>(emptySet())
    val selectedTagsStateFlow: StateFlow<Set<Int>> get() = _selectedTagsStateFlow

    init {
        loadDB()
        viewModelScope.launch {
            preloadData()
        }
    }

    private suspend fun preloadData() {
        var state = "NaN"
        _regionUidStateFlow.emit(27)
        _regionStateFlow.emit(state)
    }

    private fun loadDB() {
        viewModelScope.launch {
            database.noteDao.getAllNotes().collect { notes ->
                _notesStateFlow.emit(notes)
            }
        }
        viewModelScope.launch {
            database.tagDao.getAllTags().collect { tags ->
                _tagsStateFlow.emit(tags)
            }
        }

    }

    fun filterNotesByTags() {
        viewModelScope.launch {
            val selectedTags = _selectedTagsStateFlow.value
            if (selectedTags.isEmpty()) {
                database.noteDao.getAllNotes().collect { notes ->
                    _notesStateFlow.emit(notes)
                }
            } else {
                database.noteTagDao.getNotesByTagIds(selectedTags.toList()).collect { notes ->
                    _notesStateFlow.emit(notes)
                }
            }
        }
    }

    fun toggleTagSelection(tagId: Int) {
        viewModelScope.launch {
            val currentSelection = _selectedTagsStateFlow.value.toMutableSet()
            if (currentSelection.contains(tagId)) {
                currentSelection.remove(tagId)
            } else {
                currentSelection.add(tagId)
            }
            _selectedTagsStateFlow.emit(currentSelection)
            filterNotesByTags()
        }
    }

    fun fetchAirRaidStatus(uid: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val status = alertManager.checkAlert(uid)
                onResult(status)
            } catch (e: Exception) {
                onResult("Error: ${e.message}")
            }
        }
    }
}