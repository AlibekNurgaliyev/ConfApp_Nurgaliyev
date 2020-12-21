package kz.kolesateam.confapp.all_events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.upcoming_events.data.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.utils.model.ResponseData
import kz.kolesateam.confapp.models.ProgressState

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun getAllEventsLiveData(): LiveData<List<EventApiData>> = allEventsLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData

    fun onStart(
        branchId: Int
    ) {
        loadEvents(branchId)
    }

    private fun loadEvents(
        branchId: Int
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                allEventsRepository.getAllEvents(
                    branchId.toString()
                )
            }
            when (response) {
                is ResponseData.Success -> {
                    allEventsLiveData.value = response.result
                }
                is ResponseData.Error -> {
                    errorLiveData.value = response.error
                }
            }
            progressLiveData.value = ProgressState.Done
        }
    }
}