package com.bangkit.capstone.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.capstone.databinding.ActivityWelcomeBinding
import com.bangkit.capstone.model.UserModel
import com.bangkit.capstone.model.UserPreference
import com.google.android.gms.common.SignInButton
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.delay

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var mUserPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)
        this.supportActionBar?.hide()
//        binding.signInButton.setSize(SignInButton.SIZE_ICON_ONLY)
        setupAction()
        playSquareAnimation()
    }

    private fun playSquareAnimation() {
        val square = ObjectAnimator.ofFloat(binding.constraintLayout, View.ALPHA, 1f)
        square.duration = 1000
        square.startDelay = 2000

//        delay(2000)
        AnimatorSet().apply {
            play(square)
            start()
        }
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