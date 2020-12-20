package kz.kolesateam.confapp.all_events_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.datasource.BranchIdDataSource
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.utils.model.ResponseData
import kz.kolesateam.confapp.models.ProgressState


//private val userNameDataSource: UserNameDataSource by inject(named(MEMORY_DATA_SOURCE))
class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
    private val branchIdDataSource: BranchIdDataSource
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun getAllEventsLiveData(): LiveData<List<EventApiData>> = allEventsLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData


    fun onStart() {
        loadEvents()
    }

    private fun loadEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                allEventsRepository.getAllEvents(
                    //sharedPreferencesLoadData(this@AllEventsScreenActivity, BRANCH_ID)
                    branchIdDataSource.getBranchId().toString()
                )
            }
            when (response) {
                is ResponseData.Success -> { allEventsLiveData.value = response.result
                }
                is ResponseData.Error -> {
                    errorLiveData.value = response.error
                }
            }
            progressLiveData.value = ProgressState.Done
        }
    }
}