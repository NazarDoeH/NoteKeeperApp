package com.lab5.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lab5.data.database.entity.TagsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: TagsEntity): Long

    @Delete
    suspend fun deleteTag(tag: TagsEntity)

    @Query("SELECT * FROM Tags")
    fun getAllTags(): Flow<List<TagsEntity>>
}