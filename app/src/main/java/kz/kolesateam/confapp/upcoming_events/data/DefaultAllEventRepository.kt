package kz.kolesateam.confapp.upcoming_events.data

import kz.kolesateam.confapp.all_events.AllEventsRepository
import kz.kolesateam.confapp.upcoming_events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.utils.model.ResponseData
import retrofit2.Response
import java.lang.Exception

class DefaultAllEventRepository(
    private val upcomingEventsDataSource: UpcomingEventsDataSource
) : AllEventsRepository {

    override fun getAllEvents(
        branchIdName: String
    ): ResponseData<List<EventApiData>, String> {
        return try {
            val response: Response<List<EventApiData>> =
                upcomingEventsDataSource.getEvents(
                    branchIdName
                ).execute()

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