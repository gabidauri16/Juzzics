package com.example.juzzics.features.lyrics.di

import com.example.juzzics.features.lyrics.ui.vm.FetchLyricsVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lyricsVmModule = module {
    viewModel { FetchLyricsVM(get()) }
}