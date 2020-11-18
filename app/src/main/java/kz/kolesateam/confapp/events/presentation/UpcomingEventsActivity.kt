package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020/"
private const val SAVED_INSTANCE_TEXT = "KEY_TEXT"
private const val SAVED_INSTANCE_COLOR = "KEY_COLOR"

val apiRetrofit: Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {
    private lateinit var responseTextView: TextView
    private lateinit var syncLoadDataButton: Button
    private lateinit var asyncLoadDataButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(SAVED_INSTANCE_TEXT, responseTextView.text.toString())
            putInt(SAVED_INSTANCE_COLOR, responseTextView.currentTextColor)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        responseTextView.text = savedInstanceState.getString(SAVED_INSTANCE_TEXT)
        responseTextView.setTextColor(Color.parseColor(convertIntToHex(savedInstanceState.getInt(
            SAVED_INSTANCE_COLOR))))
    }

    private fun bindViews() {
        responseTextView = findViewById(R.id.activity_upcoming_events_text_view)
        asyncLoadDataButton = findViewById(R.id.activity_upcoming_events_button_async_load)
        syncLoadDataButton = findViewById(R.id.activity_upcoming_events_button_sync_load)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        syncLoadDataButton.setOnClickListener {
            loadApiDataSync()
        }

        asyncLoadDataButton.setOnClickListener {
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() {
        responseTextView.hide()
        progressBar.show()
        if (hasInternetConnection()) {
            Thread {
                val response: Response<ResponseBody> = apiClient.getUpcomingEventsSync().execute()
                if (response.isSuccessful) {
                    val responseBody: ResponseBody = response.body()!!
                    val responseJsonString = responseBody.string()
                    val responseJsonArray = JSONArray(responseJsonString)
                    val branchApiDataListSync = parseBranchesJsonArray(responseJsonArray)
                    runOnUiThread {
                        progressBar.hide()
                        responseTextView.show()
                        setTextAndTextColor(
                            responseTextView,
                            branchApiDataListSync.toString(),
                            this,
                            R.color.activity_upcoming_events_text_color_sync_load)
                    }
                }
            }.start()
        } else {
            progressBar.hide()
            responseTextView.show()
            setTextAndTextColor(
                responseTextView,
                getString(R.string.activity_upcoming_events_internet_access_alert),
                this,
                R.color.activity_upcoming_events_text_color_error
            )
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun loadApiDataAsync() {
        responseTextView.hide()
        progressBar.show()
        apiClient.getUpcomingEventsAsync().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>,
                                    response: Response<List<BranchApiData>>) {
                if (response.isSuccessful) {
                    progressBar.hide()
                    responseTextView.show()
                    val responseBody = response.body()!!
                    setTextAndTextColor(
                        responseTextView,
                        responseBody.toString(),
                        this@UpcomingEventsActivity,
                        R.color.activity_upcoming_events_text_color_async_load)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                progressBar.hide()
                responseTextView.show()
                setTextAndTextColor(
                    responseTextView,
                    t.localizedMessage!!,
                    this@UpcomingEventsActivity,
                    R.color.activity_upcoming_events_text_color_error)
            }
        })
    }

    private fun View.show() {
        visibility = View.VISIBLE
    }

    private fun View.hide() {
        visibility = View.GONE
    }

    private fun convertIntToHex(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }

    private fun parseBranchesJsonArray(
        responseJsonArray: JSONArray
    ): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()

        for (index in 0 until responseJsonArray.length()) {
            val branchJsonObject = (responseJsonArray[index] as? JSONObject) ?: continue
            val branchApiData = parseBranchJsonObject(branchJsonObject)
            branchList.add(branchApiData)
        }
        return branchList
    }

    private fun parseBranchJsonObject(
        branJsonObject: JSONObject
    ): BranchApiData {
        val id = branJsonObject.getInt("id")
        val title = branJsonObject.getString("title")
        val eventsJsonArray = branJsonObject.getJSONArray("events")
        val eventsApiList = mutableListOf<EventApiData>()

        for (index in 0 until eventsJsonArray.length()) {
            val eventJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue
            val eventApiData = parseEventJsonObject(eventJsonObject)
            eventsApiList.add(eventApiData)
        }
        return BranchApiData(
            id = id,
            title = title,
            events = eventsApiList
        )
    }

    private fun parseEventJsonObject(
        eventJsonObject: JSONObject
    ): EventApiData {
        val id = eventJsonObject.getInt("id")
        val startTime = eventJsonObject.getString("startTime")
        val endTime = eventJsonObject.getString("endTime")
        val title = eventJsonObject.getString("title")
        val description = eventJsonObject.getString("description")
        val place = eventJsonObject.getString("place")
        val speakerJsonObject: JSONObject? = (eventJsonObject.get("speaker") as? JSONObject)
        var speakerData: SpeakerApiData? = null
        speakerJsonObject?.let {
            speakerData = parseSpeakerJsonObject(it)
        }

        return EventApiData(
            id = id,
            startTime = startTime,
            endTime = endTime,
            title = title,
            description = description,
            place = place,
            speaker = speakerData
        )
    }

    private fun parseSpeakerJsonObject(
        speakerJsonObject: JSONObject
    ): SpeakerApiData {
        val id = speakerJsonObject.getInt("id")
        val fullName = speakerJsonObject.getString("fullName")
        val job = speakerJsonObject.getString("job")
        val photoUrl = speakerJsonObject.getString("photoUrl")

        return SpeakerApiData(
            id = id,
            fullName = fullName,
            job = job,
            photoUrl = photoUrl
        )
    }
}