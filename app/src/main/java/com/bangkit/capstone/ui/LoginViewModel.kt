package com.bangkit.capstone.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.api.ApiConfig
import com.bangkit.capstone.model.AuthApiResponse
import com.bangkit.capstone.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val _response = MutableLiveData<AuthApiResponse>()
    val response: LiveData<AuthApiResponse> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun login(user: UserModel){
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(user)
        client.enqueue(object : Callback<AuthApiResponse> {
            override fun onResponse(
                call: Call<AuthApiResponse>,
                response: Response<AuthApiResponse>
            ) {

                if (response.isSuccessful) {
                    _response.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _response.value?.status = null
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<AuthApiResponse>, t: Throwable) {

                Log.e(TAG, "onFailure: ${t.message}")
                _response.value?.status = null
                _isLoading.value = false
            }
        })
    }
}