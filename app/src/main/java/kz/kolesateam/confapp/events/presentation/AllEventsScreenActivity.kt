package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.EventRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.VIEW_HOLDER_SHARED_PREFERENCES
import kz.kolesateam.confapp.events.utils.model.ResponseData

class AllEventsScreenActivity : AppCompatActivity() {

    private lateinit var recyclerViewAllEvents: RecyclerView
    private lateinit var activityAllEventsScreenArrowBack: ImageView
    private lateinit var activityAllEventsScreenFavoriteButton: Button
    private lateinit var progressBar: ProgressBar

    private val eventsRepository: EventRepository = EventRepository()
    private val allEventsScreenAdapter: AllEventsScreenAdapter = AllEventsScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_screen)
        bindViews()
        loadEvents()
    }

    private fun bindViews() {
        recyclerViewAllEvents = findViewById(R.id.activity_all_events_screen_recycler_view)
        progressBar = findViewById(R.id.activity_all_events_screen_progress_bar)
        activityAllEventsScreenArrowBack = findViewById(R.id.activity_all_events_screen_arrow_back)
        activityAllEventsScreenFavoriteButton =
            findViewById(R.id.activity_all_events_screen_button_favorite)
        recyclerViewAllEvents.adapter = allEventsScreenAdapter

        activityAllEventsScreenArrowBack.setOnClickListener {
            navigateUpcomingEvents()
        }
        activityAllEventsScreenFavoriteButton.setOnClickListener {
            showShortToastMessage(this, "Favorites")
        }
    }

    private fun loadEvents() {
        progressBar.show()
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                eventsRepository.getEvents(getSavedUserName())
            }
            when (response) {
                is ResponseData.Success -> {
                    showResult(response.result)
                    progressBar.hide()
                }
                is ResponseData.Error -> {
                    showError(response.error)
                    progressBar.hide()
                }
            }
        }
    }

    private fun showResult(eventApiDataList: List<EventApiData>) {
        allEventsScreenAdapter.setList(eventApiDataList)
    }

    private fun showError(error: String) {
    }

    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                VIEW_HOLDER_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(BRANCH_ID, "Default Text") ?: "Default Text"
    }

    private fun navigateUpcomingEvents() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }
}