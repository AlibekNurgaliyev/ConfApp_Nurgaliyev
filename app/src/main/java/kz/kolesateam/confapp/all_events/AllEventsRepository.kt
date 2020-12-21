package kz.kolesateam.confapp.all_events

import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.utils.model.ResponseData

interface AllEventsRepository {
    fun getAllEvents(
        branchIdName: String
    ): ResponseData<List<EventApiData>, String>
}