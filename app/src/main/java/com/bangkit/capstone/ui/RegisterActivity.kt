package com.bangkit.capstone.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.R
import com.bangkit.capstone.databinding.ActivityRegisterBinding
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
        val myFormat = "dd / MM / yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.UK)
        birthDateText.setText(dateFormat.format(myCalendar.time))
    }
}