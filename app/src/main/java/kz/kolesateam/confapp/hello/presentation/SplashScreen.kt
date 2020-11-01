package kz.kolesateam.confapp.hello.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val helloIntent = Intent(this, HelloActivity::class.java)
        startActivity(helloIntent)
        finish()

    }
}