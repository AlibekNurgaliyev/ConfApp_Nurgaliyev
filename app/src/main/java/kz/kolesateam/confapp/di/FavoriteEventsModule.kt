package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.favorite_events.data.DefaultFavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val favoriteEventsModule: Module = module {

    single {
        DefaultFavoriteEventsRepository(
            context = androidApplication(),
            objectMapper = get()
        ) as FavoriteEventsRepository
    }
}
