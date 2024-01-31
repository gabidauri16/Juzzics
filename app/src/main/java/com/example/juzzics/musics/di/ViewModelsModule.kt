package com.example.juzzics.musics.di

import com.example.juzzics.musics.ui.MusicVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MusicVM(get(), get()) }
}