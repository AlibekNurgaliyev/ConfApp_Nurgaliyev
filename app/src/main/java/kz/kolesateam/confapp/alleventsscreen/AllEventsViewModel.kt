package kz.kolesateam.confapp.alleventsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BRANCH_ID
import kz.kolesateam.confapp.events.utils.model.ResponseData
import kz.kolesateam.confapp.sharedPreferencesLoadData

class AllEventsViewModel(

    private val allEventsRepository: AllEventsRepository
) : ViewModel() {
    private val resultLiveData: LiveData<String> = MutableLiveData()

    fun getResultLiveData() : LiveData<String> = resultLiveData



    private fun loadEvents() {
        progressBar.show()
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<EventApiData>, String> = withContext(Dispatchers.IO) {
                allEventsRepository.getAllEvents(
                    sharedPreferencesLoadData(this@AllEventsScreenActivity, BRANCH_ID)
                )
            }
            when (response) {
                is ResponseData.Success -> { resultLiveData.value = response
//                    showResult(response.result)
//                    progressBar.hide()
                }
                is ResponseData.Error -> {
                    showError(response.error)
                    progressBar.hide()
                }
            }
        }
    }
}