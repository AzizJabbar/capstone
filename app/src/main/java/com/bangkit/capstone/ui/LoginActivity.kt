package com.bangkit.capstone.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.databinding.ActivityLoginBinding
import com.bangkit.capstone.model.UserModel
import com.bangkit.capstone.model.UserPreference
import com.google.android.gms.common.SignInButton


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mUserPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.signInButton.setSize(SignInButton.SIZE_ICON_ONLY)
        setupAction()
    }

    private fun setupAction(){
        binding.loginButton.setOnClickListener{
            // using dummy user for authentication
            val dummyUser = UserModel("1", "dummyUser1", "a", null, "1998", 3, 4, "token1")
            mUserPreference = UserPreference(this)
            mUserPreference.setUser(dummyUser)
            startActivity(Intent(this, ChatActivity::class.java))
            finish()
        }
    }

}