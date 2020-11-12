package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventApiData (
    @JsonProperty("id")
    val id : Int?,
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("speaker")
    val speaker : SpeakerApiData?
)