package kz.kolesateam.confapp.upcoming_events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userNameTextView = itemView.findViewById<TextView>(R.id.header_name_text_view)

    fun onBind(userName: String) {
        userNameTextView.text = userName
    }
}