package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kz.kolesateam.confapp.R


const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"


class HelloActivity : AppCompatActivity() {

    private val continueButton: Button by lazy {
        findViewById(R.id.button_continue)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val editTextName: EditText = findViewById(R.id.edit_text_name)
        continueButton.setOnClickListener{
            saveName(editTextName.text.toString())
            navigationToTestHelloActivity()

        }
    }

    private fun saveName(userName: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    private fun navigationToTestHelloActivity(){
        val testIntent = Intent(this,TestHelloActivity::class.java)
        startActivity(testIntent)

    }




}