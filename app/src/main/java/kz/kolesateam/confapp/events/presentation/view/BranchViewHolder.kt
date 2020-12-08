package kz.kolesateam.confapp.events.presentation.view

import android.content.Intent
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.alleventsscreen.AllEventsScreenActivity
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.iconFavoriteClick
import kz.kolesateam.confapp.sharedPreferencesSaveData
import kz.kolesateam.confapp.showShortToastMessage

const val BRANCH_ID = "branch_id"
const val TITLE_NAME = "title_name"
const val DATE_AND_PLACE_FORMAT = "%s - %s • %s"

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    private var isIconFavoriteClicked: Boolean = false

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title

        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

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
            sharedPreferencesSaveData(itemView.context,branchApiData.id!!.toString(), BRANCH_ID)
            sharedPreferencesSaveData(itemView.context,branchApiData.title!!, TITLE_NAME)
            navigateToAllEventsScreenActivity()
        }

        branchCurrentEvent.setOnClickListener {
            showShortToastMessage(itemView.context, eventTitleCurrent.text)
        }

        branchNextEvent.setOnClickListener {
            showShortToastMessage(itemView.context, eventTitleNext.text)
        }

        iconFavoriteCurrent.setOnClickListener {
            isIconFavoriteClicked = iconFavoriteClick(isIconFavoriteClicked, iconFavoriteCurrent)
        }

        iconFavoriteNext.setOnClickListener {
            isIconFavoriteClicked = iconFavoriteClick(isIconFavoriteClicked, iconFavoriteNext)
        }
        branchItemScrollView.onScroll()
    }

    private fun navigateToAllEventsScreenActivity() {
        val intent = Intent(itemView.context, AllEventsScreenActivity::class.java)
        itemView.context.startActivity(intent)
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
}