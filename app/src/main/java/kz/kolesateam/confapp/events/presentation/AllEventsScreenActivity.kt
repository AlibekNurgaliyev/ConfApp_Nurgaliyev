package kz.kolesateam.confapp.events.presentation

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
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.events.utils.model.ResponseData

class AllEventsScreenActivity : AppCompatActivity() {

    private lateinit var recyclerViewAllEvents: RecyclerView

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
        recyclerViewAllEvents.adapter = allEventsScreenAdapter
    }

    private fun loadEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                eventsRepository.getEvents()
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
}