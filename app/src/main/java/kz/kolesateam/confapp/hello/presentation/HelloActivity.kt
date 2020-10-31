package kz.kolesateam.confapp.hello.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kz.kolesateam.confapp.R

private const val TAG = "HelloActivity"

class HelloActivity : AppCompatActivity() {

//    private val closeHelloButton: Button by lazy {
//        findViewById(R.id.activity_hello_close_hello_button)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

//        closeHelloButton.setOnClickListener {
//            finish()
//        }
    }


}