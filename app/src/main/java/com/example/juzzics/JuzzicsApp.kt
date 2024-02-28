package com.example.juzzics

import android.app.Application
import com.example.juzzics.features.musics.di.localDataModule
import com.example.juzzics.features.musics.di.repoModule
import com.example.juzzics.features.musics.di.useCasesModule
import com.example.juzzics.features.musics.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JuzzicsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    localDataModule, repoModule, useCasesModule, viewModelsModule
                )
            )
        }
    }
}