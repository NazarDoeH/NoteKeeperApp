package com.lab5.backend

import com.lab5.data.database.dao.TagsDao
import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.database.entity.TagsEntity
import com.lab5.data.database.entity.TagsNotesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TagsManager(private val database: NotekeeperDatabase) {

    fun getTagsForNoteFlow(noteId: Int): Flow<List<TagsEntity>> {
        return database.noteTagDao.getTagsForNoteIdFlow(noteId)
    }

    fun getAllTagsFlow(): Flow<List<TagsEntity>> {
        return database.tagDao.getAllTags()
    }

    suspend fun removeTagFromNote(noteId: Int, tagId: Int) {
        database.noteTagDao.deleteNoteTag(TagsNotesEntity(tagId = tagId, noteId = noteId))
    }

    suspend fun addTagForNote(noteId: Int, tagId: Int) {
        database.noteTagDao.insertNoteTag(TagsNotesEntity(tagId = tagId, noteId = noteId))
    }

    suspend fun addNewTag(name: String): Long {
        if (name.isNotBlank()) {
            return database.tagDao.insertTag(TagsEntity(name = name))
        }
        throw IllegalArgumentException("Tag name cannot be blank")
    }

    suspend fun deleteTag(tag: TagsEntity) {
        database.tagDao.deleteTag(tag)
    }
}


