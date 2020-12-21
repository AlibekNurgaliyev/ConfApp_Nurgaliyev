package kz.kolesateam.confapp.event_details.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.event_details.EVENT_ID
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.DATE_AND_PLACE_FORMAT
import kz.kolesateam.confapp.upcoming_events.utils.model.ResponseData
import org.koin.android.viewmodel.ext.android.viewModel

private const val DEFAULT_EVENT_ID = 0

class DetailsEventActivity : AppCompatActivity() {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()

    private lateinit var eventDetailsSpeakerNameTextView: TextView
    private lateinit var eventDetailsSpeakerJobTextView: TextView
    private lateinit var eventDetailsTimeAndPlace: TextView
    private lateinit var eventDetailsTitle: TextView
    private lateinit var eventDetailsDescription: TextView
    private lateinit var eventDetailsInvitedSpeaker: TextView
    private lateinit var eventDetailsSpeakersImage: ImageView
    private lateinit var eventDetailsFavoriteButton: ImageView
    private lateinit var activityAllEventsScreenArrowBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_event)
        var eventId = intent.getIntExtra(EVENT_ID, DEFAULT_EVENT_ID)
        bindViews()
        observeEventDetailsViewModel()
        eventDetailsViewModel.onStarted(
            eventId
        )
    }

    private fun bindViews() {
        eventDetailsSpeakerNameTextView = findViewById(R.id.activity_events_details_speaker_name)
        eventDetailsSpeakerJobTextView = findViewById(R.id.activity_events_details_speaker_job)
        eventDetailsTitle = findViewById(R.id.activity_events_details_title)
        eventDetailsTimeAndPlace = findViewById(R.id.activity_events_details_place_and_time)
        eventDetailsDescription = findViewById(R.id.activity_event_details_description)
        eventDetailsSpeakersImage = findViewById(R.id.activity_events_details_speaker_image_view)
        eventDetailsInvitedSpeaker = findViewById(R.id.activity_events_details_invited_speaker)
        eventDetailsFavoriteButton = findViewById(R.id.activity_events_details_favorite_image_view)
        activityAllEventsScreenArrowBack = findViewById(R.id.activity_events_details_arrow_back)

        activityAllEventsScreenArrowBack.setOnClickListener {
            finish()
        }
    }

    private fun observeEventDetailsViewModel() {
        eventDetailsViewModel.eventDetailsLiveData.observe(this, ::showEventDetails)
        eventDetailsViewModel.errorMessageLiveData.observe(this, ::showError)
    }

    private fun showEventDetails(responseData: ResponseData.Success<EventApiData>) {

        val eventDateAndPlaceText: String = DATE_AND_PLACE_FORMAT.format(
            responseData.result.startTime,
            responseData.result.endTime,
            responseData.result.place
        )

        Glide.with(this)
            .load(responseData.result.speaker?.photoUrl)
            .into(eventDetailsSpeakersImage)
        eventDetailsSpeakerNameTextView.text = responseData.result.speaker?.fullName
        eventDetailsSpeakerJobTextView.text = responseData.result.speaker?.job
        eventDetailsTimeAndPlace.text = eventDateAndPlaceText

        eventDetailsTitle.text = responseData.result.title
        eventDetailsDescription.text = responseData.result.description
        val favoriteImageResource = getFavoriteImageResource(responseData.result.isFavorite)
        eventDetailsFavoriteButton.setImageResource(favoriteImageResource)
        isSpeakerInvited(responseData.result.speaker?.isInvited!!)

        setFavouriteClickListener(responseData.result)
    }

    private fun showError(responseData: ResponseData.Error<String>) {
        Toast.makeText(this@DetailsEventActivity, responseData.error.orEmpty(), Toast.LENGTH_SHORT)
            .show()
    }

    private fun isSpeakerInvited(
        isInvited: Boolean
    ) {
        if (!isInvited) {
            eventDetailsInvitedSpeaker.visibility = View.GONE
        }
    }

    private fun setFavouriteClickListener(event: EventApiData) {
        eventDetailsFavoriteButton.setOnClickListener {
            event.isFavorite = !event.isFavorite
            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            eventDetailsFavoriteButton.setImageResource(favoriteImageResource)
            eventDetailsViewModel.onFavoriteClick(event)
        }
    }

    private fun getFavoriteImageResource(
        isFavorite: Boolean?
    ): Int =
        when (isFavorite) {
            true -> R.drawable.ic_favorite_solid
            else -> R.drawable.ic_favorite_border
        }
}