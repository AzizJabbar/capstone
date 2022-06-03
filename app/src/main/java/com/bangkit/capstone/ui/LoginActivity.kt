package com.bangkit.capstone.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        // validate input form
        validateInputText()

        binding.loginButton.setOnClickListener{
            // validate empty input & error
            if (binding.emailField.error==null && binding.passwordField.error == null && !binding.emailField.text.isNullOrEmpty() && !binding.emailField.text.isNullOrEmpty()){

                // tembak api disini
                val username = binding.emailField.text.toString().trim()
                val password = binding.passwordField.text.toString().trim()

                val loggedUser = UserModel(null, username, password)

                val loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    LoginViewModel::class.java
                )

                loginViewModel.login(loggedUser)

                loginViewModel.isLoading.observe(this){
                    showLoading(it)
                    if (it == false){
                        if (loginViewModel.response.value?.status == null){
                            Toast.makeText(this, "Incorrect email/password!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else{
                            Toast.makeText(this, "$username logged in successfully to server", Toast.LENGTH_SHORT)
                                .show()
                            mUserPreference = UserPreference(this)
                            mUserPreference.setUser(loginViewModel.response.value?.data!!)
                            startActivity(Intent(this,ChatActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun validateInputText(){

        binding.emailField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing.
            }
            override fun afterTextChanged(s: Editable) {
                // Validate Email
                if (binding.emailField.text.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.emailField.text).matches()){
                    binding.emailField.error="Please input valid email address!"
                }
            }
        })

        binding.passwordField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing.
            }
            override fun afterTextChanged(s: Editable) {
                // Validate Field
                if (binding.passwordField.text.isNullOrEmpty() || s.toString().length<6){
                    binding.passwordField.error="Password must contains at least 6 characters!"
                }
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}