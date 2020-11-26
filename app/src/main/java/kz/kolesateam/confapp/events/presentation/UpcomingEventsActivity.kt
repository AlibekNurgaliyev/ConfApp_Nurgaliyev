package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.hello.presentation.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020/"

val apiRetrofit: Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {
    private lateinit var errorDataLoadText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteButton : Button
    private val branchAdapter: BranchAdapter = BranchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        loadApiData()
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
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>,
                                    response: Response<List<BranchApiData>>) {
                progressBar.hide()
                if (response.isSuccessful) {
                    val upcomingEventsListItemList: MutableList<UpcomingEventsListItem> =
                        mutableListOf()
                    val headerListItem: UpcomingEventsListItem = UpcomingEventsListItem(
                        type = 1,
                        data = String.format(resources.getString(R.string.activity_upcoming_events_shared_prefs_name),
                            getSavedUserName())
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

    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(USER_NAME_KEY, "Default Text") ?: "Default Text"
    }

    private fun View.show() {
        visibility = View.VISIBLE
    }

    private fun View.hide() {
        visibility = View.GONE
    }
}