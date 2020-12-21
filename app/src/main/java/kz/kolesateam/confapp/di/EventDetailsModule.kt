package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.event_details.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.event_details.data.EventDetailsDataSource
import kz.kolesateam.confapp.event_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.event_details.presentation.EventDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventDetailsModule: Module = module {

    viewModel {
        EventDetailsViewModel(
            eventDetailsRepository = get(),
            favoriteEventsRepository = get()
        )
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventDetailsDataSource::class.java)
    }

    factory {
        DefaultEventDetailsRepository(
            eventDetailsDataSource = get()
        ) as EventDetailsRepository
    }
}