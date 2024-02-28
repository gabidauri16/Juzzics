package com.example.juzzics.features.musics.di

import com.example.juzzics.features.musics.data.localProvider.MusicLocalProvider
import com.example.juzzics.features.musics.data.localProvider.MusicLocalProviderImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataModule = module {
    single { MusicLocalProviderImpl(get()) } bind MusicLocalProvider::class
}