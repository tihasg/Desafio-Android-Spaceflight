package com.spaceflight

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import com.spaceflight.di.applicationModule
import com.spaceflight.di.repositoryModule
import com.spaceflight.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DesafioAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instace = applicationContext

        setupHawk()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@DesafioAplication)
            androidFileProperties()
            modules(applicationModule, viewModelModule, repositoryModule)
        }
    }

    private fun setupHawk() = Hawk.init(this).build()


    companion object {
        lateinit var instace: Context
    }
}