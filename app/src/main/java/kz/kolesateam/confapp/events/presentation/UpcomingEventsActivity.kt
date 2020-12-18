package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.MEMORY_DATA_SOURCE
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter

import kz.kolesateam.confapp.hide
import kz.kolesateam.confapp.show
import kz.kolesateam.confapp.setTextAndTextColor
import kz.kolesateam.confapp.sharedPreferencesLoadData
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val DEFAULT_USER_NAME = "Nonoame"

class UpcomingEventsActivity : AppCompatActivity() {
    private val upcomingEventsDataSource: UpcomingEventsDataSource by inject()
    private val userNameDataSource: UserNameDataSource by inject(named(
        MEMORY_DATA_SOURCE))


    private lateinit var errorDataLoadText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteButton: Button

    private lateinit var userName: String
    private val branchAdapter: BranchAdapter = BranchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        initViews()
        loadApiData()

    }

    private fun initViews() {
        userName = userNameDataSource.getUserName() ?: DEFAULT_USER_NAME
    }

    private fun bindViews() {
        errorDataLoadText = findViewById(R.id.activity_upcoming_events_data_load_failed)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        favoriteButton = findViewById(R.id.activity_upcoming_events_button_favorite)
        recyclerView.adapter = branchAdapter

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun loadApiData() {
        progressBar.show()
        upcomingEventsDataSource.getUpcomingEvents()
            .enqueue(object : Callback<List<BranchApiData>> {
                override fun onResponse(call: Call<List<BranchApiData>>,
                                        response: Response<List<BranchApiData>>) {
                    progressBar.hide()
                    if (response.isSuccessful) {
                        val upcomingEventsListItemList: MutableList<UpcomingEventsListItem> =
                            mutableListOf()
                        val headerListItem = UpcomingEventsListItem(
                            type = 1,
                            data = String.format(resources.getString(R.string.activity_upcoming_events_shared_prefs_name),
                                userName
//                                sharedPreferencesLoadData(this@UpcomingEventsActivity,
//                                    USER_NAME_KEY)
                            )
                        )
                        val branchListItemList: List<UpcomingEventsListItem> =
                            response.body()!!.map { branchApiData ->
                                UpcomingEventsListItem(
                                    type = 2,
                                    data = branchApiData
                                )
                            }
                        upcomingEventsListItemList.add(headerListItem)
                        upcomingEventsListItemList.addAll(branchListItemList)
                        branchAdapter.setList(upcomingEventsListItemList)
                    }
                }

                override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                    progressBar.hide()
                    favoriteButton.hide()
                    errorDataLoadText.show()
                    setTextAndTextColor(
                        errorDataLoadText,
                        t.localizedMessage!!,
                        this@UpcomingEventsActivity,
                        R.color.activity_upcoming_events_text_color_error)
                }
            })
    }
}