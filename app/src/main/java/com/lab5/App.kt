package com.lab5

import android.app.Application
import com.lab5.backend.AlertManager
import com.lab5.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        //Ініціалізація Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}