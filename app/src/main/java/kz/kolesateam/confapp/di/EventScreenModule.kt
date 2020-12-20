package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.all_events_screen.AllEventsRepository
import kz.kolesateam.confapp.all_events_screen.AllEventsViewModel
import kz.kolesateam.confapp.events.data.DefaultAllEventRepository
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventScreenModule: Module = module {

    single {
        val retrofit: Retrofit = get()
        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory<AllEventsRepository> {
        DefaultAllEventRepository(upcomingEventsDataSource = get())
    }

    viewModel {
        AllEventsViewModel(
            allEventsRepository = get()
        )
    }
}