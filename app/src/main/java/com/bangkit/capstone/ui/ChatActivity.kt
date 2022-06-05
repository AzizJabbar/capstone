package com.bangkit.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.R
import com.bangkit.capstone.adapters.ChatAdapter
import com.bangkit.capstone.databinding.ActivityChatBinding
import com.bangkit.capstone.model.ChatModel
import com.bangkit.capstone.model.UserPreference
import com.bangkit.capstone.viewmodel.ChatViewModel
import com.bangkit.capstone.viewmodel.ViewModelFactory.Companion.getInstance
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var viewModel: ChatViewModel
    private lateinit var adapterChat: ChatAdapter
    private lateinit var foodName: String
    private var formId = 0
    private var isRecom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)

        //force app to use light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(binding.root)

        //initiate adapter
        adapterChat = ChatAdapter(this)

        //initiate viewmodel
        viewModel = obtainViewModel(this@ChatActivity)

        //initiate user pref
        mUserPreference = UserPreference(this)

        //get user
        val user = mUserPreference.getUser()

        //greet user with their name
        botSendMessage("Hi ${user.fullName?.split(" ")?.get(0)}, How can I help you?")

        //listen to send button
        binding.btnSend.setOnClickListener{ processInput() }

        //load all message history from database
        loadMessage()

        //scroll to bottom
        scrollToBottom()

        // checking user pref
        mUserPreference = UserPreference(this)
        if (mUserPreference.getUser().token.isNullOrEmpty()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun scrollToBottom() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff: Int = binding.root.rootView.height - binding.root.height
            if (heightDiff > 100) {
                binding.recyclerViewChat.smoothScrollToPosition(if(adapterChat.itemCount!=0) adapterChat.itemCount - 1 else 0)

            }
        }
    }

    private fun processInput() {
        val input = binding.textInput.text.toString() //Get message from input field
        if (input != ""){

            val message = ChatModel(
                0,
                input,
                Date().time,
                1,
                false
            ) //Create new Chat Entity
            viewModel.insert(message) // Insert to DB

            binding.textInput.setText("")// Empty the field

            if (isRecom){ // if current state is in recommendation session
                foodName = input
                showForm()
            }
            else{
                predictMessage(input)
            }
        }
    }

    private fun showForm() {
        val message = ChatModel(
            0,
            "kapan?",
            Date().time,
            3,
            false
        )
        formId = viewModel.getNewestChat().id + 1
        viewModel.insert(message)
    }

    private fun predictMessage(input: String) {
        viewModel.predictChat(input)
        viewModel.getPredictedLabel().observe(this){
            when (it) {
                "greeting" -> {
                    //TODO: Implement greeting messages
                    botSendMessage("halo juga")
                    viewModel.resetLabel()
                }
                "rekomendasi" -> {
                    //TODO: Implement food recommendation
                    isRecom = true
                    botSendMessage("nama makanan?")
                    viewModel.resetLabel()
                }
                "" -> {
                }
            }
        }
    }

    fun submitTime(sarapan: Boolean, makanSiang: Boolean, makanMalam: Boolean, snack: Boolean){
        viewModel.deleteChatById(formId.toString())
//        viewModel.getChatById(formId.toString()).isSubmitted = true
//        viewModel.submitted(formId.toString())
        botSendMessage("$foodName, $sarapan, $makanSiang, $makanMalam, $snack")
        val temp = mutableListOf<String>()
        if(sarapan)temp.add("1")
        if(makanSiang)temp.add("2")
        if(makanMalam)temp.add("3")
        if(snack)temp.add("4")
        viewModel.predictFood(foodName, temp.joinToString())
        viewModel.getRecommendation().observe(this){
            if (it != null) {
                botSendMessage(it.toString())
                viewModel.resetRecommendation()
            }
        }
        formId = 0
        foodName = ""
        isRecom = false
    }

    private fun botSendMessage(s: String) {
        val message = ChatModel(
            0,
            s,
            Date().time,
            2,
            false
        )
        viewModel.insert(message)

    }

    private fun loadMessage() {
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapterChat
        viewModel.getChats().observe(this){ chats ->
            if(chats != null){
                adapterChat.submit(chats)
                scrollToBottom()
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
                true
            }
            R.id.app_bar_delete_chat_history -> {
                viewModel.deleteAllChats()
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