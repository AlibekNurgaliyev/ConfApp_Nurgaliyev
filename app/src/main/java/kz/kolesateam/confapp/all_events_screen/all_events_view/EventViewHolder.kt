package kz.kolesateam.confapp.all_events_screen.all_events_view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.iconFavoriteClick
import kz.kolesateam.confapp.showShortToastMessage
import kz.kolesateam.confapp.events.presentation.view.DATE_AND_PLACE_FORMAT

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val eventDateAndPlace: TextView = view.findViewById(R.id.date_and_place_text)
    private val speakerName: TextView = view.findViewById(R.id.event_speaker_name)
    private val speakerCompany: TextView = view.findViewById(R.id.event_company_name)
    private val eventTitle: TextView = view.findViewById(R.id.event_title)
    private val branchEvent: View = view.findViewById(R.id.branch_current_event)

    private val iconFavorite: ImageView =
        view.findViewById(R.id.event_favorite_icon)

    private var isIconFavoriteClicked: Boolean = false

    init {
        view.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    fun bind(eventApiData: EventApiData) {
        val eventDateAndPlaceText: String = DATE_AND_PLACE_FORMAT.format(
            eventApiData.startTime,
            eventApiData.endTime,
            eventApiData.place
        )

        eventDateAndPlace.text = eventDateAndPlaceText
        speakerName.text = eventApiData.speaker?.fullName ?: "noname"
        speakerCompany.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title

        branchEvent.setOnClickListener {
            itemView.context.showShortToastMessage(eventTitle.text)
        }

        iconFavorite.setOnClickListener {
            isIconFavoriteClicked = iconFavoriteClick(isIconFavoriteClicked, iconFavorite)
        }
    }
}