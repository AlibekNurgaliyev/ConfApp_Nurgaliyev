package kz.kolesateam.confapp

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

fun TextView.setTextAndTextColor(
    text: String,
    colorId: Int
) {
    this.text = text
    this.setTextColor(ContextCompat.getColor(this.context, colorId))
}

fun Context.showShortToastMessage(
    toastText: CharSequence
) {
    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
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