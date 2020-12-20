package kz.kolesateam.confapp.events.data.datasource

import kz.kolesateam.confapp.events.data.models.EventApiData

interface FavoriteClickListener {
    fun onClick(eventData: EventApiData) {

    }
}