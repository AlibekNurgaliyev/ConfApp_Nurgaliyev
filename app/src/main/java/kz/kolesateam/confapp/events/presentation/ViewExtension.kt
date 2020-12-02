package kz.kolesateam.confapp.events.presentation

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kz.kolesateam.confapp.R

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
