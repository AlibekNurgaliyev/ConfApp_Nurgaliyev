package kz.kolesateam.confapp.all_events

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.all_events.all_events_view.EventViewListener
import kz.kolesateam.confapp.event_details.EventDetailsRouter
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.showShortToastMessage
import org.koin.android.viewmodel.ext.android.viewModel

class AllEventsScreenActivity : AppCompatActivity(), EventViewListener {

    private lateinit var recyclerViewAllEvents: RecyclerView
    private lateinit var activityAllEventsScreenArrowBack: ImageView
    private lateinit var activityAllEventsScreenFavoriteButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var activityAllEventsScreenEventName: TextView

    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val allEventsScreenAdapter: AllEventsScreenAdapter = AllEventsScreenAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_screen)
        val branchId = intent.getIntExtra("branchId", 0)
        val branchTitle = intent.getStringExtra("branchTitle")
        bindViews(branchTitle)
        observeAllEventsViewModel()
        allEventsViewModel.onStart(branchId)
    }

    private fun bindViews(
        branchTitle: String?
    ) {
        recyclerViewAllEvents = findViewById(R.id.activity_all_events_screen_recycler_view)
        progressBar = findViewById(R.id.activity_all_events_screen_progress_bar)
        activityAllEventsScreenArrowBack = findViewById(R.id.activity_all_events_screen_arrow_back)
        activityAllEventsScreenEventName = findViewById(R.id.activity_all_events_screen_event_name)
        activityAllEventsScreenFavoriteButton =
            findViewById(R.id.activity_all_events_screen_button_favorite)
        recyclerViewAllEvents.adapter = allEventsScreenAdapter

        activityAllEventsScreenArrowBack.setOnClickListener {
            navigateUpcomingEvents()
        }
        activityAllEventsScreenFavoriteButton.setOnClickListener {
            showShortToastMessage("Favorites")
        }
        activityAllEventsScreenEventName.text = branchTitle
    }

    private fun observeAllEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
        allEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showResult(eventApiDataList: List<EventApiData>) {
        allEventsScreenAdapter.setList(eventApiDataList)
    }

    private fun showError(error: String) {
    }

    private fun navigateUpcomingEvents() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(position: Int, theme: String, eventId: Int) {
        startActivity(EventDetailsRouter().createIntent(this, eventId))
    }
}