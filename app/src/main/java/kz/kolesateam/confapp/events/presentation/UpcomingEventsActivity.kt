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
import androidx.core.content.ContextCompat
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
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
        responseTextView.text = ""
        progressBar.show()
        if (hasInternetConnection()) {
            Thread {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        progressBar.hide()
                        setTextAndTextColor(body,
                            R.color.activity_upcoming_events_text_color_sync_load)
                    }
                }
            }.start()
        } else {
            progressBar.hide()
            setTextAndTextColor(getString(R.string.activity_upcoming_events_internet_access_alert),
                R.color.activity_upcoming_events_text_color_error)
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun loadApiDataAsync() {
        responseTextView.text = ""
        progressBar.show()
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {
            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    progressBar.hide()
                    val body: JsonNode = response.body()!!
                    setTextAndTextColor(body,
                        R.color.activity_upcoming_events_text_color_async_load)
                }
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                progressBar.hide()
                setTextAndTextColor(t.localizedMessage, R.color.activity_upcoming_events_text_color_error)
            }
        })
    }

    private fun setTextAndTextColor(body: JsonNode, colorId: Int) {
        responseTextView.text = body.toString()
        responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, colorId))
    }

    private fun setTextAndTextColor(textValue: String, colorId: Int) {
        responseTextView.text = textValue
        responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, colorId))
    }

    private fun View.show(): View {
        if (visibility != View.VISIBLE) visibility = View.VISIBLE
        return this
    }

    private fun View.hide(): View {
        if (visibility != View.GONE) visibility = View.GONE
        return this
    }

    private fun convertIntToHex(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }
}