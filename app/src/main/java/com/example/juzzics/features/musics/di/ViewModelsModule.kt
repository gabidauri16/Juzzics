package com.example.juzzics.features.musics.di

import com.example.juzzics.features.home.vm.HomeVM
import com.example.juzzics.features.musics.ui.vm.MusicVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val musicViewModelsModule = module {
    viewModel { MusicVM(get(), get()) }
    viewModel { HomeVM() }
}