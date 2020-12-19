package kz.kolesateam.confapp.alleventsscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.DefaultAllEventRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.events.presentation.view.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.TITLE_NAME
import kz.kolesateam.confapp.events.utils.model.ResponseData
import kz.kolesateam.confapp.sharedPreferencesLoadData
import kz.kolesateam.confapp.show
import kz.kolesateam.confapp.showShortToastMessage
import kz.kolesateam.confapp.hide
import org.koin.android.viewmodel.ext.android.viewModel

class AllEventsScreenActivity : AppCompatActivity() {

    private lateinit var recyclerViewAllEvents: RecyclerView
    private lateinit var activityAllEventsScreenArrowBack: ImageView
    private lateinit var activityAllEventsScreenFavoriteButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var activityAllEventsScreenEventName: TextView

    //private val eventsRepositoryDefaultAll: DefaultAllEventRepository = DefaultAllEventRepository()
    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val allEventsScreenAdapter: AllEventsScreenAdapter = AllEventsScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_screen)
        bindViews()
//        loadEvents()
    }

    private fun bindViews() {
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
            showShortToastMessage(this, "Favorites")
        }
        activityAllEventsScreenEventName.text = sharedPreferencesLoadData(this, TITLE_NAME)
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
}