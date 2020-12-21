package kz.kolesateam.confapp.event_details.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData

interface EventDetailsRepository {

    @WorkerThread
    suspend fun getEventDetails(
        result: (EventApiData) -> Unit,
        fail: (String?) -> Unit,
        eventId: Int
    )
}