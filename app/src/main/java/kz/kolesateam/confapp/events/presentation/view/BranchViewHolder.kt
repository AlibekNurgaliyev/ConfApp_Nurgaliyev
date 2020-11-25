package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    private val branchTitleAndArrow: LinearLayout =
        itemView.findViewById(R.id.branch_title_and_arrow_layout)
    private val iconFavoriteCurrent: ImageView =
        branchCurrentEvent.findViewById(R.id.event_favorite_icon)
    private val iconFavoriteNext: ImageView = branchNextEvent.findViewById(R.id.event_favorite_icon)
    private var isIconFavoriteClicked: Boolean = false

    private val eventDateAndPlaceCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.date_and_place_text)
    private val speakerNameCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.event_speaker_name)
    private val speakerCompanyCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.event_company_name)
    private val eventTitleCurrent: TextView = branchCurrentEvent.findViewById(R.id.event_title)

    private val eventDateAndPlaceNext: TextView =
        branchNextEvent.findViewById(R.id.date_and_place_text)
    private val speakerNameNext: TextView = branchNextEvent.findViewById(R.id.event_speaker_name)
    private val speakerCompanyNext: TextView = branchNextEvent.findViewById(R.id.event_company_name)
    private val eventTitleNext: TextView = branchNextEvent.findViewById(R.id.event_title)

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

        val currentEventDateAndPlaceText: String = "%s - %s • %s".format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place
        )
        eventDateAndPlaceCurrent.text = currentEventDateAndPlaceText
        speakerNameCurrent.text = currentEvent.speaker?.fullName ?: "noname"
        speakerCompanyCurrent.text = currentEvent.speaker?.job
        eventTitleCurrent.text = currentEvent.title

        val nextEventDateAndPlaceText: String = "%s - %s • %s".format(
            nextEvent.startTime,
            nextEvent.endTime,
            nextEvent.place
        )
        eventDateAndPlaceNext.text = nextEventDateAndPlaceText
        speakerNameNext.text = nextEvent.speaker?.fullName ?: "noname"
        speakerCompanyNext.text = nextEvent.speaker?.job
        eventTitleNext.text = nextEvent.title

        branchTitleAndArrow.setOnClickListener {
            Toast.makeText(itemView.context, branchTitle.text, Toast.LENGTH_SHORT).show()
        }
        branchCurrentEvent.setOnClickListener {
            Toast.makeText(itemView.context, eventTitleCurrent.text, Toast.LENGTH_SHORT).show()
        }
        branchNextEvent.setOnClickListener {
            Toast.makeText(itemView.context, eventTitleNext.text, Toast.LENGTH_SHORT).show()
        }
        iconFavoriteCurrent.setOnClickListener {
            isIconFavoriteClicked = when (isIconFavoriteClicked) {
                true -> {
                    iconFavoriteCurrent.setImageResource(R.drawable.ic_favorite_border)
                    false
                }
                false -> {
                    iconFavoriteCurrent.setImageResource(R.drawable.ic_favorite_solid)
                    true
                }
            }
        }

        iconFavoriteNext.setOnClickListener {
            isIconFavoriteClicked = when (isIconFavoriteClicked) {
                true -> {
                    iconFavoriteNext.setImageResource(R.drawable.ic_favorite_border)
                    false
                }
                false -> {
                    iconFavoriteNext.setImageResource(R.drawable.ic_favorite_solid)
                    true
                }
            }
        }
    }
}