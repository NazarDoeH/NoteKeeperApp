package com.lab5.ui.screens.tagEditor

import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.database.entity.TagsEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.backend.TagsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TagEditorViewModel(
    private val database: NotekeeperDatabase,
    val tagsManager: TagsManager
) : ViewModel() {


    val tags: Flow<List<TagsEntity>> = database.tagDao.getAllTags()

    fun deleteTag(tag: TagsEntity) {
        viewModelScope.launch {
            tagsManager.deleteTag(tag)
        }
    }

    fun addTag(name: String) {
        viewModelScope.launch {
            if(name != "") {
                tagsManager.addNewTag(name)
            }
        }
    }
}
