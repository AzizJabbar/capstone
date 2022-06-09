package com.bangkit.capstone.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.api.ApiConfig
import com.bangkit.capstone.database.ChatRepository
import com.bangkit.capstone.model.*
import com.bangkit.capstone.ui.RegisterViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private val mChatRepository: ChatRepository = ChatRepository(application)
    private val _predictedLabel = MutableLiveData<String>()
    private val predictedLabel : LiveData<String> = _predictedLabel

    private val _recommendation = MutableLiveData<FoodPredictResponse?>()
    private val recommendation : LiveData<FoodPredictResponse?> = _recommendation

    private val _flag = MutableLiveData<String?>()
    private val flag : LiveData<String?> = _flag

    fun insert(chat: ChatModel) = mChatRepository.insert(chat)
    fun getChats():LiveData<List<ChatModel>> = mChatRepository.getAllChats()
    fun deleteAllChats() = mChatRepository.deleteAllChats()
    fun getNewestChat(): ChatModel = mChatRepository.getNewestChat()
    fun getChatById(id: String): ChatModel = mChatRepository.getChatById(id)
    fun deleteChatById(id: String) = mChatRepository.deleteChatById(id)
    fun submitted(id: String) = mChatRepository.submitted(id)

    fun predictChat(input: String){
        val client = ApiConfig.getApiService().predictChat("Bearer " + pref.getUser().token, ChatInput(input))
        client.enqueue(object : Callback<ChatPredictResponse> {
            override fun onResponse(
                call: Call<ChatPredictResponse>,
                response: Response<ChatPredictResponse>
            ) {
                if (response.isSuccessful) {
                    _predictedLabel.value = response.body()?.data?.predictedLabel ?: ""
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatPredictResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun predictFood(foodName: String, time: String){
        val client = ApiConfig.getApiService().predictFood("Bearer " + pref.getUser().token, FoodInput(foodName, time))
        client.enqueue(object : Callback<FoodPredictResponse> {
            override fun onResponse(
                call: Call<FoodPredictResponse>,
                response: Response<FoodPredictResponse>
            ) {
                if (response.isSuccessful) {
                    _recommendation.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    _flag.value = "empty"
                }
            }

            override fun onFailure(call: Call<FoodPredictResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getPredictedLabel() = predictedLabel

    fun getRecommendation() = recommendation

    fun getFlag() = flag

    fun resetLabel() { _predictedLabel.value = "" }
    fun resetRecommendation() { _recommendation.value = null }
    fun resetFlag() { _flag.value = "" }
}