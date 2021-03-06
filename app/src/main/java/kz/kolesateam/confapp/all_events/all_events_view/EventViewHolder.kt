package kz.kolesateam.confapp.all_events.all_events_view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.iconFavoriteClick
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.DATE_AND_PLACE_FORMAT

class EventViewHolder(
    view: View,
    eventViewListener: EventViewListener
) : RecyclerView.ViewHolder(view) {
    private val eventDateAndPlace: TextView = view.findViewById(R.id.date_and_place_text)
    private val speakerName: TextView = view.findViewById(R.id.event_speaker_name)
    private val speakerCompany: TextView = view.findViewById(R.id.event_company_name)
    private val eventTitle: TextView = view.findViewById(R.id.event_title)

    private val iconFavorite: ImageView =
        view.findViewById(R.id.event_favorite_icon)

    private var isIconFavoriteClicked: Boolean = false

    private var eventId: Int = 0

    init {
        view.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
        view.setOnClickListener {
            eventViewListener.onItemClick(adapterPosition, eventTitle.text.toString(), eventId)
        }
    }

    fun bind(eventApiData: EventApiData) {
        eventId = eventApiData.id!!

        val eventDateAndPlaceText: String = DATE_AND_PLACE_FORMAT.format(
            eventApiData.startTime,
            eventApiData.endTime,
            eventApiData.place
        )

        eventDateAndPlace.text = eventDateAndPlaceText
        speakerName.text = eventApiData.speaker?.fullName ?: "noname"
        speakerCompany.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title

        iconFavorite.setOnClickListener {
            isIconFavoriteClicked = iconFavoriteClick(isIconFavoriteClicked, iconFavorite)
        }
    }
}