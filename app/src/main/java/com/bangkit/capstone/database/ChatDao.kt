package com.bangkit.capstone.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.capstone.model.ChatModel

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(chat: ChatModel)

    @Query("SELECT * from ChatModel")
    fun getAllChats(): LiveData<List<ChatModel>>

    @Query("DELETE from ChatModel")
    fun deleteAllChats()
}