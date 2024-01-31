package com.example.juzzics.musics.di

import com.example.juzzics.musics.data.repo.MusicRepoImpl
import com.example.juzzics.musics.domain.repo.MusicRepo
import org.koin.dsl.bind
import org.koin.dsl.module


val repoModule = module {
    single { MusicRepoImpl(get()) } bind MusicRepo::class
}