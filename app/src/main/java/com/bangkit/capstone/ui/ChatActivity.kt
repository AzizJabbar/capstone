package com.bangkit.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
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
import com.google.gson.Gson
import java.lang.Math.pow
import java.util.*
import kotlin.math.pow

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

        // checking user pref
        if (mUserPreference.getUser().token.isNullOrEmpty()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        } else{
            if((Date().time - mUserPreference.getTime() ) / 60000 >= 30){
                logout()
                finish()
            }
            //get user
            val user = mUserPreference.getUser()

            // greet user for the first time only
            if (viewModel.getNewestChat() == null){
                //greet user with their name
                botSendMessage("Hi ${user.fullName?.split(" ")?.get(0)}, How can I help you?")
                botSendMessage("Your BMI is: ${(user.weight?.div(user.height!!.toDouble().pow(2.0)/10000) ?:"" )} kg/m\u00B2")
                val bmi = user.weight?.div(user.height!!.toDouble().pow(2.0)/10000)
                var lowerBoundWeight = 18.6 * user.height!! * user.height!! / 10000
                var higherBoundWeight = 25 * user.height!! * user.height!! / 10000
                var kategori: String
                kategori = "Not Found"
                if (bmi != null) {
                    when {
                        bmi < 16 -> kategori = "Severe Thinness"
                        bmi > 16 && bmi <= 17 -> kategori = "Moderate Thinness"
                        bmi > 17 && bmi <= 18.5 -> kategori = "Mild Thinness"
                        bmi > 18.5 && bmi <= 25 -> kategori = "Normal"
                        bmi > 25 && bmi <= 30 -> kategori = "Overweight"
                        bmi > 30 && bmi <= 35 -> kategori = "Obese Class I"
                        bmi > 35 && bmi <= 40 -> kategori = "Obese Class II"
                        bmi > 40 -> kategori = "Obese Class II"
                    }
                }

                if (bmi != null) {
                    when{
                        bmi < 16 -> botSendMessage("Your Category is: $kategori <br> <br>" +
                                "Recommendation: Gains ${lowerBoundWeight - user.weight!!} kg to reach normal level of BMI <br> <br>" +
                                "I will help you achieve that by recommending proper food plan!")
                        bmi > 18.5 && bmi <= 25 -> botSendMessage("Your Category is: $kategori <br> <br>" +
                                "Recommendation: Keep up the good work! <br> <br>" +
                                "I will help you maintaining your healthy weight by recommending proper food plan!")
                        bmi > 25 -> botSendMessage("Your Category is: $kategori <br> <br>" +
                                "Recommendation: Lose ${user.weight!! - higherBoundWeight} kg to reach normal level of BMI <br> <br>" +
                                "I will help you achieve that by recommending proper food plan!")
                    }
                }

            }

        }

        //listen to send button
        binding.btnSend.setOnClickListener{ processInput() }

        //load all message history from database
        loadMessage()

        //scroll to bottom
        scrollToBottom()

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
                false,
                null
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
            "Pilih waktu rekomendasi:",
            Date().time,
            3,
            false,
            null
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
                    botSendMessage("Apa nama makanan yang sudah kamu makan?")
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
//        botSendMessage("$foodName, $sarapan, $makanSiang, $makanMalam, $snack")
        val temp = mutableListOf<String>()
        if(sarapan)temp.add("1")
        if(makanSiang)temp.add("2")
        if(makanMalam)temp.add("3")
        if(snack)temp.add("4")
        viewModel.predictFood(foodName, temp.joinToString())
        viewModel.getRecommendation().observe(this){
            if (it != null) {
                val map = mapOf(
                    "Sarapan" to it.data.breakfast?.recommended,
                    "Makan Siang" to it.data.lunch?.recommended ,
                    "Makan Malam" to it.data.dinner?.recommended,
                    "Snack" to it.data.snack?.recommended
                )
                val giziSarapan = it.data.breakfast?.giziNeeded
                val giziLunch = it.data.lunch?.giziNeeded
                val giziDinner = it.data.dinner?.giziNeeded
                val giziSnack = it.data.snack?.giziNeeded
                botSendMessage("Berikut adalah rekomendasi dari saya")
                map.forEach { (key, value) ->
                    if (value != null) {
                        when {
                            key == "Sarapan" -> botSendMessage("Total Gizi yang diperlukan untuk $key: <br> <br>"
                                    + "Total energi: ${giziSarapan?.energi} kkal <br>" +
                            "Karbohidrat total: ${giziSarapan?.karbohidratTotal} gram <br>" +
                            "Lemak total: ${giziSarapan?.lemakTotal} gram <br>" +
                            "Protein: ${giziSarapan?.protein} gram")
                        key == "Makan Siang" -> botSendMessage("Total Gizi yang diperlukan untuk $key: <br> <br>"
                                    + "Total energi: ${giziLunch?.energi} kkal <br>" +
                                    "Karbohidrat total: ${giziLunch?.karbohidratTotal} gram <br>" +
                                    "Lemak total: ${giziLunch?.lemakTotal} gram <br>" +
                                    "Protein: ${giziLunch?.protein} gram")
                        key == "Makan Malam" -> botSendMessage("Total Gizi yang diperlukan untuk $key: <br> <br>"
                                    + "Total energi: ${giziDinner?.energi} kkal <br>" +
                                    "Karbohidrat total: ${giziDinner?.karbohidratTotal} gram <br>" +
                                    "Lemak total: ${giziDinner?.lemakTotal} gram <br>" +
                                    "Protein: ${giziDinner?.protein} gram")
                        key == "Snack" -> botSendMessage("Total Gizi yang diperlukan untuk $key: <br> <br>"
                                    + "Total energi: ${giziSnack?.energi} kkal <br>" +
                                    "Karbohidrat total: ${giziSnack?.karbohidratTotal} gram <br>" +
                                    "Lemak total: ${giziSnack?.lemakTotal} gram <br>" +
                                    "Protein: ${giziSnack?.protein} gram")
                        }

//                        val rec = mutableListOf<String>()
//                        value.forEach { recommendItem ->
//                            rec.add("- ${recommendItem.namaMakanan}")
//                        }
//                        botSendMessage("<b>${key}</b>:<br>${rec.joinToString("<br>")}")
                        val message = ChatModel(
                            0,
                            "Rekomendasi Makanan untuk $key (geser ke kanan untuk melihat menu selanjutnya!)<br>",
                            Date().time,
                            4,
                            false,
                            Gson().toJson(value)
                        )
                        viewModel.insert(message)

                    }
                }

//                botSendMessage(it.toString())
                viewModel.resetRecommendation()
            }
            viewModel.getFlag().observe(this){ flag ->
                if (flag == "empty"){
                    botSendMessage("Maaf, nama makanan tersebut tidak ada di database kami")
                    viewModel.resetFlag()
                }
            }
        }
        formId = 0
        foodName = ""
        isRecom = false
        temp.clear()
    }

    private fun botSendMessage(s: String) {
        val message = ChatModel(
            0,
            s,
            Date().time,
            2,
            false,
            null
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
                viewModel.deleteAllChats()
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