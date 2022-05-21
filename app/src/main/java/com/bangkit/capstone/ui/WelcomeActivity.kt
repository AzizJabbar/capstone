package com.bangkit.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.databinding.ActivityWelcomeBinding
import com.bangkit.capstone.model.UserModel
import com.bangkit.capstone.model.UserPreference

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var mUserPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction(){
        binding.loginButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerButton.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}