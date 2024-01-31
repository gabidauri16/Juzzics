package com.example.juzzics.musics.di

import com.example.juzzics.musics.domain.usecases.GetAllLocalMusicFilesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetAllLocalMusicFilesUseCase(get()) }
}