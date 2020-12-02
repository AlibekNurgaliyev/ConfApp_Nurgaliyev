package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_EVENT_URL = "http://37.143.8.68:2020/"

interface ApiClient {

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

//    @GET("/branch_events/{branch_id}")
    @GET("/branch_events/0")

//    fun getEvents(@Path(value = "branchId") branchId:String): Call<List<EventApiData>>
    fun getEvents(): Call<List<EventApiData>>
    companion object RetrofitClient {
        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_EVENT_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}