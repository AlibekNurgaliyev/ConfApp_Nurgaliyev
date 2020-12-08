package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.di.eventScreenModule
import org.koin.core.context.startKoin

class ConfAppApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(eventScreenModule)
        }
    }
}