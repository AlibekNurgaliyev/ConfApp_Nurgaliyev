package kz.kolesateam.confapp.events.data

import android.content.Context
import android.content.SharedPreferences
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.events.presentation.view.VIEW_HOLDER_SHARED_PREFERENCES
import kz.kolesateam.confapp.events.utils.model.ResponseData
import kz.kolesateam.confapp.hello.presentation.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY
import retrofit2.Response
import java.lang.Exception

class EventRepository() {

    private val apiClient: ApiClient = ApiClient.create()

    fun getEvents(branchIdName: String): ResponseData<List<EventApiData>, String> {
        return try {

            val response: Response<List<EventApiData>> = apiClient.getEvents(branchIdName).execute()

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