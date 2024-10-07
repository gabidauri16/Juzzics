package com.example.juzzics.features.musics.di

import com.example.juzzics.features.musics.domain.usecases.GetAllLocalMusicFilesUseCase
import org.koin.dsl.module

val musicUseCasesModule = module {
    factory { GetAllLocalMusicFilesUseCase(get()) }
}