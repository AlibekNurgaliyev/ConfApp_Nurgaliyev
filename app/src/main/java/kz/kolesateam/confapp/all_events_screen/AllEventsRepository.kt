package kz.kolesateam.confapp.all_events_screen

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.utils.model.ResponseData

interface AllEventsRepository {
    fun getAllEvents(
        branchIdName: String
    ): ResponseData<List<EventApiData>, String>
}