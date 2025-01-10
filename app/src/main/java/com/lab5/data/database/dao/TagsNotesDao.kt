package com.lab5.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.entity.TagsEntity
import com.lab5.data.database.entity.TagsNotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsNotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTag(noteTag: TagsNotesEntity)

    @Delete
    suspend fun deleteNoteTag(noteTag: TagsNotesEntity)

    @Query("DELETE FROM NoteTags WHERE note_id = :noteId")
    suspend fun deleteNoteTagsForNoteId(noteId: Int)

    @Query(
        """
        SELECT * FROM Notes 
        WHERE id IN (
            SELECT note_id FROM NoteTags WHERE tag_id IN (:tagIds)
        )
        ORDER BY edit_time DESC
        """
    )
    fun getNotesByTagIds(tagIds: List<Int>): Flow<List<NotesEntity>>

    @Query(
        """
        SELECT Tags.* FROM Tags 
        INNER JOIN NoteTags ON Tags.id = NoteTags.tag_id
        WHERE NoteTags.note_id = :noteId
        """
    )
    fun getTagsForNoteIdFlow(noteId: Int): Flow<List<TagsEntity>>

    @Update
    suspend fun update(note: NotesEntity)
}