package com.lab5.data.database.entity

import androidx.room.*

@Entity(
    tableName = "NoteTags",
    primaryKeys = ["tag_id", "note_id"],
    foreignKeys = [
        ForeignKey(
            entity = TagsEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NotesEntity::class,
            parentColumns = ["id"],
            childColumns = ["note_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["tag_id"], unique = false), Index(value = ["note_id"], unique = false)]
)
data class TagsNotesEntity(
    @ColumnInfo(name = "tag_id") val tagId: Int,
    @ColumnInfo(name = "note_id") val noteId: Int
)
