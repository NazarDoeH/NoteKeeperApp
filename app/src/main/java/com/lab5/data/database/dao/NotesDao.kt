package com.lab5.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lab5.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesEntity): Long

    @Delete
    suspend fun deleteNote(note: NotesEntity)

    @Query("SELECT * FROM Notes ORDER BY edit_time DESC")
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM Notes WHERE id = :id")
    fun getNoteById(id: Int): NotesEntity?

    @Update
    suspend fun update(note: NotesEntity)
}