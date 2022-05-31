package com.bangkit.capstone.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.R
import com.bangkit.capstone.databinding.ActivityRegisterBinding
import com.bangkit.capstone.model.UserModel
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {
    val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var birthDateText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }
        birthDateText = binding.birthDate
        birthDateText.setOnClickListener {
            DatePickerDialog(
                this@RegisterActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        heightEditText = binding.heightField
        heightEditText.setOnClickListener{
            openHeightDialog()
        }
        weightEditText = binding.weightField
        weightEditText.setOnClickListener {
            openWeightDialog()
        }

        setupAction()

    }

    private fun openWeightDialog() {
        val linearLayout = layoutInflater.inflate(R.layout.view_number_dialog, null) as LinearLayout
        val numberpicker = linearLayout.findViewById<View>(R.id.numberPicker1) as NumberPicker
        numberpicker.minValue = 0
        numberpicker.maxValue = 250
        numberpicker.value = Integer.parseInt(if(weightEditText.text.toString()=="") "60" else weightEditText.text.toString())
        val builder: AlertDialog = AlertDialog.Builder(this)
            .setPositiveButton("Submit", null)
            .setNegativeButton("Cancel", null)
            .setView(linearLayout)
            .setCancelable(false)
            .setTitle("Select your weight")
            .create()
        builder.show()
        //Setting up OnClickListener on positive button of AlertDialog
        //Setting up OnClickListener on positive button of AlertDialog
        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            weightEditText.setText(numberpicker.value.toString())
            builder.hide()
        }
    }

    private fun openHeightDialog() {
        val linearLayout = layoutInflater.inflate(R.layout.view_number_dialog, null) as LinearLayout
        val numberpicker = linearLayout.findViewById<View>(R.id.numberPicker1) as NumberPicker
        numberpicker.minValue = 0
        numberpicker.maxValue = 250
        numberpicker.value = Integer.parseInt(if(heightEditText.text.toString()=="") "160" else heightEditText.text.toString())
        val builder: AlertDialog = AlertDialog.Builder(this)
            .setPositiveButton("Submit", null)
            .setNegativeButton("Cancel", null)
            .setView(linearLayout)
            .setCancelable(false)
            .setTitle("Select your height")
            .create()
        builder.show()
        //Setting up OnClickListener on positive button of AlertDialog
        //Setting up OnClickListener on positive button of AlertDialog
        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            heightEditText.setText(numberpicker.value.toString())
            builder.hide()
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.UK)
        birthDateText.setText(dateFormat.format(myCalendar.time))
    }

    private fun setupAction(){
        // handling view error
        // untuk input text, handle saat text changed
        // untuk non text, handle saat button sign up di klik
        validateInputText()

        if (binding.emailField.error == null && binding.fullnameField.error == null && binding.passwordField.error == null){
            // binding button sign up
            binding.registerButton.setOnClickListener {

                if (binding.emailField.error == null && binding.fullnameField.error == null
                    && binding.passwordField.error == null && !binding.emailField.text.isNullOrEmpty()
                    && !binding.fullnameField.text.isNullOrEmpty() && !binding.passwordField.text.isNullOrEmpty()){
                    // binding view to required api body data type
                    val username = binding.emailField.text.toString().trim()
                    val fullName = binding.fullnameField.text.toString().trim()
                    val password = binding.passwordField.text.toString().trim()

                    val checkHeight = binding.heightField.text.toString().trim()
                    var height: Int?
                    if (checkHeight.isNullOrEmpty()){
                        binding.heightField.error = "Please fill this field!"
                        height = null
                    }
                    else {
                        binding.heightField.error = null
                        height = binding.heightField.text.toString().trim().toInt()
                    }

                    val checkWeight = binding.weightField.text.toString().trim()
                    var weight: Int?
                    if (checkWeight.isNullOrEmpty()){
                        binding.weightField.error = "Please fill this field!"
                        weight = null
                    }
                    else {
                        binding.weightField.error = null
                        weight = binding.weightField.text.toString().trim().toInt()
                    }


                    val birthDate = binding.birthDate.text.toString().trim()
                    if (birthDate.isNullOrEmpty()){
                        binding.birthDate.error = "Please fill this field!"
                    }
                    else{
                        binding.birthDate.error = null
                    }

                    var gender: String
                    // handling radio button
                    if (binding.genderField.checkedRadioButtonId == -1){
                        // if radio button has not been checked
                        binding.radioMale.error = "Please fill this field"
                        gender = ""
                    }
                    else{
                        binding.radioMale.error = null
                        val radioButton = binding.genderField.checkedRadioButtonId
                        gender = resources.getResourceEntryName(radioButton).trim()
                        gender = if (gender == "radio_male") "male" else "female"
                    }

                    if (binding.heightField.error == null && binding.weightField.error == null
                        && binding.radioMale.error == null && binding.birthDate.error == null){
                        val user = UserModel(null, username, password, fullName, gender, birthDate, height, weight, "token1"  )
                        Toast.makeText(this, "Field: $user ", Toast.LENGTH_LONG).show()

                        // tembak api disini
                        val registerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                            RegisterViewModel::class.java
                        )

                        registerViewModel.createUser(user.username!!, user.password!!, user.fullName!!, user.gender!!, user.height!!, user.weight!!, user.date_of_birth!!)
                        registerViewModel.isLoading.observe(this){
                            showLoading(it)
                            if (it == false){
                                if (registerViewModel.response.value?.status.equals("error")){
                                    Toast.makeText(this, "Email is already taken, please input another email!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else{
                                    Toast.makeText(this, "Successfully Registered " + user.username + " to Server", Toast.LENGTH_SHORT)
                                        .show()
                                    startActivity(Intent(this, WelcomeActivity::class.java))
                                    finish()
                                }
                            }
                        }

                    }
                }

            }
        }

    }

    private fun validateInputText(){

        binding.emailField.addTextChangedListener(object: TextWatcher{
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

        binding.fullnameField.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing.
            }
            override fun afterTextChanged(s: Editable) {
                // Validate Field
                if (binding.fullnameField.text.isNullOrEmpty()){
                    binding.fullnameField.error="Please fill this field!"
                }
            }
        })

        binding.passwordField.addTextChangedListener(object: TextWatcher{
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