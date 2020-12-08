package kz.kolesateam.confapp.hello.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.sharedPreferencesSaveData

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
            sharedPreferencesSaveData(this, nameEditText.text.toString(), USER_NAME_KEY)
            navigateToTestHelloActivity()
        }
    }

    private fun navigateToTestHelloActivity() {
        val testIntent = Intent(this, UpcomingEventsActivity::class.java)
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