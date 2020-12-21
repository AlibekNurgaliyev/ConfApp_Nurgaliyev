package kz.kolesateam.confapp.upcoming_events.data.datasource

import kz.kolesateam.confapp.upcoming_events.data.models.BranchApiData
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UpcomingEventsDataSource {

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    @GET("/branch_events/{branch_id}")

    fun getEvents(
        @Path(value = "branch_id") branchId: String
    ): Call<List<EventApiData>>
}