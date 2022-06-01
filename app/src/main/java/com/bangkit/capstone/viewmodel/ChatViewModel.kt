package com.bangkit.capstone.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.database.ChatRepository
import com.bangkit.capstone.model.ChatModel
import com.bangkit.capstone.model.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private val mChatRepository: ChatRepository = ChatRepository(application)
    fun insert(chat: ChatModel) = mChatRepository.insert(chat)
    fun getChats():LiveData<List<ChatModel>> = mChatRepository.getAllChats()
    fun deleteAllChats() = mChatRepository.deleteAllChats()
}