package kz.kolesateam.confapp.event_details.data

import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsDataSource {
    @GET("/events/{id}")
    fun getEventsDetails(
        @Path("id") id: Int
    ): Call<EventApiData>
}