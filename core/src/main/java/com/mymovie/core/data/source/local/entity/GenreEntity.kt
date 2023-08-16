package com.mymovie.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int
)
