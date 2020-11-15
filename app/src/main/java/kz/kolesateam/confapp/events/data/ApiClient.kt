package kz.kolesateam.confapp.events.data

import com.fasterxml.jackson.databind.JsonNode
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<JsonNode>
}