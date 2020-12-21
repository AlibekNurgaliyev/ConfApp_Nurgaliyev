package kz.kolesateam.confapp.upcoming_events.data.datasource

import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData

interface FavoriteClickListener {
    fun onClick(eventData: EventApiData) {
    }
}