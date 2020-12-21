package kz.kolesateam.confapp.event_details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.event_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.utils.model.ResponseData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository

const val DEFAULT_BRANCH_ID = 0

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository,
    private val favoriteEventsRepository: FavoriteEventsRepository
) : ViewModel() {

    val eventDetailsLiveData: MutableLiveData<ResponseData.Success<EventApiData>> =
        MutableLiveData()
    val errorMessageLiveData: MutableLiveData<ResponseData.Error<String>> = MutableLiveData()

    private val eventIdLiveData: MutableLiveData<Int> = MutableLiveData()

    fun onStarted(
        eventId: Int,
    ) {
        eventIdLiveData.value = eventId
        getEventDetails()
    }

    private fun getEventDetails() = viewModelScope.launch {
        eventDetailsRepository.getEventDetails(
            result = {
                eventDetailsLiveData.value = ResponseData.Success(prepareEvent(it))
            },
            fail = {
                errorMessageLiveData.value = ResponseData.Error(it.toString())
            },
            eventId = eventIdLiveData.value ?: DEFAULT_BRANCH_ID
        )
    }

    fun onFavoriteClick(
        eventData: EventApiData
    ) {
        when (eventData.isFavorite) {
            true -> {
                favoriteEventsRepository.saveFavoriteEvent(eventData)
            }
            else -> {
                favoriteEventsRepository.removeFavoriteEvent(eventId = eventData.id)
            }
        }
    }

    private fun prepareEvent(
        event: EventApiData
    ): EventApiData = setFavorite(event)

    private fun setFavorite(
        event: EventApiData
    ): EventApiData {
        event.isFavorite = favoriteEventsRepository.isFavorite(event.id)
        return event
    }
}