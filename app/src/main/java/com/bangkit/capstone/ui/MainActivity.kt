package com.bangkit.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.databinding.ActivityMainBinding
import com.bangkit.capstone.model.UserPreference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserPreference: UserPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // checking user pref
        mUserPreference = UserPreference(this)
        if (mUserPreference.getUser().token.isNullOrEmpty()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
        else{
            setupAction()
        }
    }

    private fun setupAction(){
        binding.logoutButton.setOnClickListener{
            mUserPreference.logout()
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}