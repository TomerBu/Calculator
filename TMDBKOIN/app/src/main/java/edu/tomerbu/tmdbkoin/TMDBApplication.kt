package edu.tomerbu.tmdbkoin

import android.app.Application
import edu.tomerbu.tmdbkoin.di.appModule
import edu.tomerbu.tmdbkoin.di.networkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TMDBApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@TMDBApplication)
            modules(appModule(), networkingModule())
        }
    }
}
