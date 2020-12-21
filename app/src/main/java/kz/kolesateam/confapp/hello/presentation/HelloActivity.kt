package kz.kolesateam.confapp.hello.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.MEMORY_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {

    private val userNameDataSource: UserNameDataSource by inject(named(MEMORY_DATA_SOURCE))

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
            saveUserName(nameEditText.text.toString())
            navigateToTestHelloActivity()
        }
    }

    private fun saveUserName(name: String) {
        userNameDataSource.saveUserName(name)
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