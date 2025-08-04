package br.com.ramonmluz.moviehub

import android.app.Application
import br.com.ramonmluz.moviehub.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE) // Alterado para ERROR para não poluir tanto o logcat, mas ainda ver erros de Koin. Use Level.DEBUG para mais detalhes.
            androidContext(this@MyApplication)
            modules(allModules)
        }
    }
}
