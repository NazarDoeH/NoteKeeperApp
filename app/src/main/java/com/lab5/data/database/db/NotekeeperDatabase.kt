package com.lab5.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.lab5.data.database.dao.NotesDao
import com.lab5.data.database.dao.TagsDao
import com.lab5.data.database.dao.TagsNotesDao
import com.lab5.data.database.entity.NotesEntity
import com.lab5.data.database.entity.TagsEntity
import com.lab5.data.database.entity.TagsNotesEntity
import java.sql.Date

//База данних
@Database(entities = [TagsEntity::class, NotesEntity::class, TagsNotesEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotekeeperDatabase : RoomDatabase() {
    abstract val tagDao: TagsDao
    abstract val noteDao: NotesDao
    abstract val noteTagDao: TagsNotesDao
}
//Конвертація дати
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}