package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {

    private val continueButton: Button by lazy {
        findViewById(R.id.button_continue)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val nameEditText: EditText = findViewById(R.id.edit_text_name)
        nameEditText.addTextChangedListener(textWatcher)
        continueButton.setOnClickListener {
            saveName(nameEditText.text.toString())
            navigateToTestHelloActivity()
        }
    }

    private fun saveName(userName: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    private fun navigateToTestHelloActivity() {
        val testIntent = Intent(this, TestHelloActivity::class.java)
        startActivity(testIntent)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(p0: Editable?) {
            continueButton.isEnabled = !p0.toString().isBlank() && !p0.toString().contains(" ")
        }
    }
}