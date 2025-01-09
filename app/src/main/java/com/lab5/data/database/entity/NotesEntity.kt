package com.lab5.data.database.entity

import androidx.room.*
import java.sql.Date

//Таблиця Notes
@Entity(tableName = "Notes")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "edit_time") val editTime: Date
)