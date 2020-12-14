package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.utils.model.ResponseData
import retrofit2.Response
import java.lang.Exception

class EventRepository() {

    private val upcomingEventsDataSource: UpcomingEventsDataSource = UpcomingEventsDataSource.create()

    fun getEvents(branchIdName: String): ResponseData<List<EventApiData>, String> {
        return try {
            val response: Response<List<EventApiData>> = upcomingEventsDataSource.getEvents(branchIdName).execute()

            if (response.isSuccessful) {
                ResponseData.Success(response.body()!!)
            } else {
                ResponseData.Error("Error occurred!")
            }
        } catch (e: Exception) {
            ResponseData.Error(e.localizedMessage ?: "Error occured")
        }
    }
}