package kz.kolesateam.confapp.events.presentation

import android.app.Activity
import android.widget.TextView
import androidx.core.content.ContextCompat

fun setTextAndTextColor(textView: TextView, body: String, activityName: Activity, colorId: Int) {
    textView.text = body
    textView.setTextColor(ContextCompat.getColor(activityName, colorId))
}