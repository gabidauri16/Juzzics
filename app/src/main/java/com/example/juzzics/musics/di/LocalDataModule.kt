package com.example.juzzics.musics.di

import com.example.juzzics.musics.data.localProvider.MusicLocalProvider
import com.example.juzzics.musics.data.localProvider.MusicLocalProviderImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataModule = module {
    single { MusicLocalProviderImpl(get()) } bind MusicLocalProvider::class
}