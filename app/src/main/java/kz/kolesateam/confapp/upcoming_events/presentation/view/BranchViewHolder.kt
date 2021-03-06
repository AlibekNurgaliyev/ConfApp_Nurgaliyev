package kz.kolesateam.confapp.upcoming_events.presentation.view

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.upcoming_events.data.datasource.FavoriteClickListener
import kz.kolesateam.confapp.upcoming_events.data.models.BranchApiData
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.BranchTitleClickListener

const val DATE_AND_PLACE_FORMAT = "%s - %s • %s"

class BranchViewHolder(
    itemView: View,
    recyclerViewListener: RecyclerViewListener,
    private val favoriteClickListener: FavoriteClickListener,
    private val branchTitleClickListener: BranchTitleClickListener
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    private val branchItemScrollView: HorizontalScrollView =
        itemView.findViewById(R.id.branch_item_scroll_view)
    private val branchTitleAndArrow: LinearLayout =
        itemView.findViewById(R.id.branch_title_and_arrow_layout)

    private val iconFavoriteCurrent: ImageView =
        branchCurrentEvent.findViewById(R.id.event_favorite_icon)
    private val iconFavoriteNext: ImageView =
        branchNextEvent.findViewById(R.id.event_favorite_icon)

    private val eventDateAndPlaceCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.date_and_place_text)
    private val speakerNameCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.event_speaker_name)
    private val speakerCompanyCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.event_company_name)
    private val eventTitleCurrent: TextView = branchCurrentEvent.findViewById(R.id.event_title)

    private val eventDateAndPlaceNext: TextView =
        branchNextEvent.findViewById(R.id.date_and_place_text)
    private val speakerNameNext: TextView =
        branchNextEvent.findViewById(R.id.event_speaker_name)
    private val speakerCompanyNext: TextView =
        branchNextEvent.findViewById(R.id.event_company_name)
    private val eventTitleNext: TextView =
        branchNextEvent.findViewById(R.id.event_title)

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE

        branchCurrentEvent.setOnClickListener {
            recyclerViewListener.onCurrentBrunchClick(
                adapterPosition,
                eventTitleCurrent.text.toString(),
                currentEvent.id!!

            )
        }

        branchNextEvent.setOnClickListener {
            recyclerViewListener.onCurrentBrunchClick(
                adapterPosition,
                eventTitleNext.text.toString(),
                nextEvent.id!!
            )
        }
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title

        if (branchApiData.events.isEmpty()) {
            branchCurrentEvent.visibility = View.GONE
            branchNextEvent.visibility = View.GONE
            return
        }

        currentEvent = branchApiData.events.first()
        nextEvent = branchApiData.events.last()

        val currentEventDateAndPlaceText: String = DATE_AND_PLACE_FORMAT.format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place
        )
        eventDateAndPlaceCurrent.text = currentEventDateAndPlaceText
        speakerNameCurrent.text = currentEvent.speaker?.fullName ?: "noname"
        speakerCompanyCurrent.text = currentEvent.speaker?.job
        eventTitleCurrent.text = currentEvent.title

        val nextEventDateAndPlaceText: String = DATE_AND_PLACE_FORMAT.format(
            nextEvent.startTime,
            nextEvent.endTime,
            nextEvent.place
        )

        eventDateAndPlaceNext.text = nextEventDateAndPlaceText
        speakerNameNext.text = nextEvent.speaker?.fullName ?: "noname"
        speakerCompanyNext.text = nextEvent.speaker?.job
        eventTitleNext.text = nextEvent.title

        branchTitleAndArrow.setOnClickListener {
            branchTitleClickListener.onBranchTitleClick(branchApiData)
        }

        iconFavoriteCurrent.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            iconFavoriteCurrent.setImageResource(favoriteImageResource)
            favoriteClickListener.onClick(currentEvent)
        }

        iconFavoriteNext.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            iconFavoriteNext.setImageResource(favoriteImageResource)
            favoriteClickListener.onClick(nextEvent)
        }
        branchItemScrollView.onScroll()

        val favoriteImageResourceCurrent = getFavoriteImageResource(currentEvent.isFavorite)
        iconFavoriteCurrent.setImageResource(favoriteImageResourceCurrent)

        val favoriteImageResourceNext = getFavoriteImageResource(nextEvent.isFavorite)
        iconFavoriteNext.setImageResource(favoriteImageResourceNext)
    }

    private fun HorizontalScrollView.onScroll() {
        branchItemScrollView.viewTreeObserver?.addOnScrollChangedListener {
            if (scrollX >= maxScrollAmount * 0.9) {
                branchNextEvent.setBackgroundResource(R.drawable.bg_events_card_active)
                branchCurrentEvent.setBackgroundResource(R.drawable.bg_events_card_shadowed)
            }
            if (scrollX == 0) {
                branchNextEvent.setBackgroundResource(R.drawable.bg_events_card_shadowed)
                branchCurrentEvent.setBackgroundResource(R.drawable.bg_events_card_active)
            }
        }
    }

    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_solid
        else -> R.drawable.ic_favorite_border
    }
}