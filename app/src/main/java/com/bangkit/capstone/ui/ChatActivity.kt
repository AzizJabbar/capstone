package com.bangkit.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.R
import com.bangkit.capstone.adapters.ChatAdapter
import com.bangkit.capstone.databinding.ActivityChatBinding
import com.bangkit.capstone.databinding.ActivityLoginBinding
import com.bangkit.capstone.model.ChatModel
import com.bangkit.capstone.model.UserPreference
import com.bangkit.capstone.viewmodel.ChatViewModel
import com.bangkit.capstone.viewmodel.ViewModelFactory.Companion.getInstance
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this@ChatActivity)
//        botSendMessage("Hi, How can I help you?")
        binding.btnSend.setOnClickListener {
            val input = binding.textInput.text.toString()
            val message = ChatModel(
                0,
                input,
                Date().time,
                1
            )
            viewModel.insert(message)
            binding.textInput.setText("")
        }

        loadMessage()


//        val listViewType = mutableListOf<Int>()
//        listViewType.add(1)
//        listViewType.add(2)
//        listViewType.add(1)
//        listViewType.add(2)
//        val listChat = mutableListOf<ChatModel>()
//        listChat.add(ChatModel(text = "Hello", sent = "16:36", type = 1))
//        listChat.add(ChatModel(text = "Hi", sent = "16:40", type = 2))
//        listChat.add(ChatModel(text = "How are you?", sent = "16:41", type = 1))
//        listChat.add(ChatModel(text = "I'm fine, Thanks. You?", sent = "16:42", type = 2))
//        val adapterChat = ChatAdapter(listViewType = listViewType, listChat = listChat)
//        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
//        binding.recyclerViewChat.adapter = adapterChat


        // checking user pref
        mUserPreference = UserPreference(this)
        if (mUserPreference.getUser().token.isNullOrEmpty()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun botSendMessage(s: String) {
        val message = ChatModel(
            0,
            s,
            Date().time,
            2
        )
        viewModel.insert(message)

    }

    private fun loadMessage() {
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        val adapterChat = ChatAdapter()
        binding.recyclerViewChat.adapter = adapterChat
        viewModel.getChats().observe(this){ chats ->
            if(chats != null){

                adapterChat.submit(chats)
            }
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
//                toLoginActivity()
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

    private fun obtainViewModel(activity: AppCompatActivity): ChatViewModel {
        val factory = getInstance(activity.application, UserPreference.getInstance(activity))
        return ViewModelProvider(activity, factory)[ChatViewModel::class.java]
    }

}