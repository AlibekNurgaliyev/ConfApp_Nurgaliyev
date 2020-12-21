package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.all_events.AllEventsRepository
import kz.kolesateam.confapp.all_events.AllEventsViewModel
import kz.kolesateam.confapp.upcoming_events.data.DefaultAllEventRepository
import kz.kolesateam.confapp.upcoming_events.data.datasource.UpcomingEventsDataSource
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