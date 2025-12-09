package br.com.ramonmluz.moviehub

import android.app.Application
import br.com.ramonmluz.moviehub.di.MovieModules
import br.com.ramonmluz.moviehub.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            MovieModules().load()
            NetworkModule().load()
        }
    }
}
