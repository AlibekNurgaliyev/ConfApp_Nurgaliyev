package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.EventRepository
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.events.presentation.view.VIEW_HOLDER_SHARED_PREFERENCES
import kz.kolesateam.confapp.events.utils.model.ResponseData
import kz.kolesateam.confapp.hello.presentation.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY

class AllEventsScreenActivity : AppCompatActivity() {

    private lateinit var recyclerViewAllEvents: RecyclerView

    private val eventsRepository: EventRepository = EventRepository()
    private val allEventsScreenAdapter: AllEventsScreenAdapter = AllEventsScreenAdapter()

//    private var savedId = getSavedUserName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_screen)
        bindViews()
        loadEvents()
    }

    private fun bindViews() {
        recyclerViewAllEvents = findViewById(R.id.activity_all_events_screen_recycler_view)
        recyclerViewAllEvents.adapter = allEventsScreenAdapter
    }

    private fun loadEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                val ssa = getSavedUserName()
                eventsRepository.getEvents(getSavedUserName())
            }
            when (response) {
                is ResponseData.Success -> showResult(response.result)
                is ResponseData.Error -> showError(response.error)
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
}