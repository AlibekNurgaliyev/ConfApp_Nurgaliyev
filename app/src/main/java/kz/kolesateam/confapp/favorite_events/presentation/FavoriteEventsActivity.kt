package kz.kolesateam.confapp.favorite_events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import org.koin.android.ext.android.inject

class FavoriteEventsActivity : AppCompatActivity() {

    private val favoriteEventsRepository : FavoriteEventsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        val favoriteTextView :TextView =findViewById(R.id.favorite_text)
        favoriteTextView.text = favoriteEventsRepository.getAllFavoriteEvents().toString()
    }
}