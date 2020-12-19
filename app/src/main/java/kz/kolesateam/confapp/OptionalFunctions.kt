package kz.kolesateam.confapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.hello.presentation.APPLICATION_SHARED_PREFERENCES

fun setTextAndTextColor(textView: TextView, body: String, activityName: Activity, colorId: Int) {
    textView.text = body
    textView.setTextColor(ContextCompat.getColor(activityName, colorId))
}

fun showShortToastMessage(context: Context, toastText: CharSequence) {
    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
}

fun iconFavoriteClick(isIconFavoriteClicked: Boolean, iconFavorite: ImageView): Boolean {
    if (isIconFavoriteClicked) {
        iconFavorite.setImageResource(R.drawable.ic_favorite_border)
        return false
    } else {
        iconFavorite.setImageResource(R.drawable.ic_favorite_solid)
        return true
    }
}

fun onFavoriteClick(eventData:EventApiData) {
    UpcomingEventsActivity.onFavoriteClick(eventData)
}

fun sharedPreferencesSaveData(currentContext: Context, userName: String, constant: String) {
    val sharedPreferences: SharedPreferences =
        currentContext.getSharedPreferences(
            APPLICATION_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString(constant, userName)
    editor.apply()
}

fun sharedPreferencesLoadData(currentActivity: Activity, constant: String): String {
    val sharedPreferences: SharedPreferences =
        currentActivity.getSharedPreferences(
            APPLICATION_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    return sharedPreferences.getString(constant, "Default Text") ?: "Default Text"
}