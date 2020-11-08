package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020/"

val apiRetrofit: Retrofit = Retrofit.Builder()
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

    private fun bindViews() {
        responseTextView = findViewById(R.id.activity_upcoming_events_text_view)
        asyncLoadDataButton = findViewById(R.id.activity_upcoming_events_button_async_load)
        syncLoadDataButton = findViewById(R.id.activity_upcoming_events_button_sync_load)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        syncLoadDataButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loadApiDataSync()
        }

        asyncLoadDataButton.setOnClickListener {
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() {
        progressBar.visibility = View.INVISIBLE
        if (hasInternetConnection()) {
            Thread {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        responseTextView.text = body.toString()
                        responseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_text_color_sync_load))
                    }
                }
            }.start()
        } else {
            responseTextView.text =
                getString(R.string.activity_upcoming_events_internet_access_alert)
            responseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_text_color_error))
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun loadApiDataAsync() {
        progressBar.visibility = View.INVISIBLE
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {
            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    responseTextView.text = body.toString()
                    responseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_text_color_async_load))
                }
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                responseTextView.text = t.localizedMessage
                responseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_text_color_error))
            }
        })
    }
}