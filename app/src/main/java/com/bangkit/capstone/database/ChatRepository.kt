package com.bangkit.capstone.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.capstone.model.ChatModel
import com.example.mygithubuserapp.ChatRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatRepository(application: Application) {
    private val mChatsDao: ChatDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = ChatRoomDatabase.getDatabase(application)
        mChatsDao = db.chatDao()
    }
    fun getAllChats(): LiveData<List<ChatModel>> = mChatsDao.getAllChats()
    fun insert(chat: ChatModel) {
        executorService.execute { mChatsDao.insert(chat) }
    }
    fun deleteAllChats() = mChatsDao.deleteAllChats()
    fun getNewestChat(): ChatModel = mChatsDao.getNewestChat()
    fun getChatById(id: String) = mChatsDao.getChatById(id)
    fun deleteChatById(id: String) = mChatsDao.deleteChatById(id)
    fun submitted(id: String) = mChatsDao.submitted(id)
}