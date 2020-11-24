package kz.kolesateam.confapp.events.presentation

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

fun setTextAndTextColor(textView: TextView, body: String, activityName: Activity, colorId: Int) {
    textView.text = body
    textView.setTextColor(ContextCompat.getColor(activityName, colorId))
}

fun showToast(activityName: Activity, toastMessage: String, duration: String) {
    if (duration == "long")
        Toast.makeText(activityName, toastMessage, Toast.LENGTH_LONG).show()
    else if (duration == "short")
        Toast.makeText(activityName, toastMessage, Toast.LENGTH_SHORT).show()
}