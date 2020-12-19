package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.utils.model.ResponseData
import java.lang.Exception

interface FavoriteEventsRepository {
    fun saveFavoriteEvent(
        eventApiData: EventApiData
    )

    fun removeFavoriteEvent(
        eventId: Int?
    )

    fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception>
}