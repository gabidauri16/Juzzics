package com.example.juzzics.features.lyrics.di

import com.example.juzzics.features.lyrics.domain.usecase.FetchLyricsUseCase
import org.koin.dsl.module

val lyricsUseCasesModule = module {
    factory { FetchLyricsUseCase(get()) }
}