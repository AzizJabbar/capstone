package com.bangkit.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.R
import com.bangkit.capstone.adapters.ChatAdapter
import com.bangkit.capstone.databinding.ActivityChatBinding
import com.bangkit.capstone.databinding.ActivityLoginBinding
import com.bangkit.capstone.model.ChatModel
import com.bangkit.capstone.model.UserPreference

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var mUserPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listViewType = mutableListOf<Int>()
        listViewType.add(1)
        listViewType.add(2)
        listViewType.add(1)
        listViewType.add(2)
        val listChat = mutableListOf<ChatModel>()
        listChat.add(ChatModel(text = "Hello", sent = "16:36", type = 1))
        listChat.add(ChatModel(text = "Hi", sent = "16:40", type = 2))
        listChat.add(ChatModel(text = "How are you?", sent = "16:41", type = 1))
        listChat.add(ChatModel(text = "I'm fine, Thanks. You?", sent = "16:42", type = 2))
        val adapterChat = ChatAdapter(listViewType = listViewType, listChat = listChat)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapterChat


        // checking user pref
        mUserPreference = UserPreference(this)
        if (mUserPreference.getUser().token.isNullOrEmpty()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_logout -> {
                logout()
                toLoginActivity()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun toLoginActivity() {
        startActivity(Intent(this@ChatActivity, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    private fun logout() {
        mUserPreference.logout()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

}