package com.bangkit.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.databinding.ActivityLoginBinding
import com.bangkit.capstone.model.UserModel
import com.bangkit.capstone.model.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mUserPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        binding.loginButton.setOnClickListener{
            // using dummy user for authentication
            val dummyUser = UserModel("1", "dummyUser1", "token1")
            mUserPreference = UserPreference(this)
            mUserPreference.setUser(dummyUser)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}