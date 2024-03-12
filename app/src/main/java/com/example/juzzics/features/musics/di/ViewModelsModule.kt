package com.example.juzzics.features.musics.di

import com.example.juzzics.features.home.HomeVM
import com.example.juzzics.features.musics.ui.MusicVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MusicVM(get(), get(), get(), get()) }
    viewModel { HomeVM() }
}