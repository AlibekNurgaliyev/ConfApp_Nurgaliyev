package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.all_events_screen.AllEventsViewModel
import kz.kolesateam.confapp.events.data.DefaultAllEventRepository
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_EVENT_URL = "http://37.143.8.68:2020/"

val eventScreenModule: Module = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_EVENT_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory {
        DefaultAllEventRepository(upcomingEventsDataSource = get())
    }

    viewModel {
        AllEventsViewModel(allEventsRepository = get(),
        branchIdDataSource = get(named(BRANCH_ID_DATA_SOURCE)))
    }

}
