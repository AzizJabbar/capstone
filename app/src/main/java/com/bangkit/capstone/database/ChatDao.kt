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

    @Query("SELECT * FROM ChatModel ORDER BY ID DESC LIMIT 1")
    fun getNewestChat(): ChatModel

    @Query("SELECT * FROM ChatModel WHERE id=:id ")
    fun getChatById(id: String): ChatModel

    @Query("DELETE FROM ChatModel WHERE id=:id")
    fun deleteChatById(id: String)

    @Query("UPDATE ChatModel SET isSubmitted=1 WHERE id = :id")
    fun submitted(id: String)
}