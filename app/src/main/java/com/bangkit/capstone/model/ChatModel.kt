package com.bangkit.capstone.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "isSubmitted")
    var isSubmitted: Boolean,

    @ColumnInfo(name = "data")
    var data: String?
)
