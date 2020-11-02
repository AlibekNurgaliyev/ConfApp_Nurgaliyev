package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R

class TestHelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)
        val userNameText: TextView = findViewById(R.id.test_hello_activity_output)
        userNameText.text = getSavedUserName()
    }

    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(USER_NAME_KEY, "Default Text") ?: "Default Text"
    }
}