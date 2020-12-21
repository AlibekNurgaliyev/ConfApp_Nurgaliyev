package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                eventScreenModule,
                applicationModule,
                userNameModule,
                favoriteEventsModule,
                branchIdModule,
                eventDetailsModule
            )
        }
    }
}