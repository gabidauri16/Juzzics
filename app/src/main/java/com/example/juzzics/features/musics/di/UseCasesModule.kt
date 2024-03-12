package com.example.juzzics.features.musics.di

import com.example.juzzics.features.musics.domain.usecases.GetAllLocalMusicFilesUseCase
import com.example.juzzics.features.musics.domain.usecases.vmLogics.PlayMusicLogic
import com.example.juzzics.features.musics.domain.usecases.vmLogics.PlayNextOrPrevLogic
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetAllLocalMusicFilesUseCase(get()) }
    factory { PlayMusicLogic() }
    factory { PlayNextOrPrevLogic() }
}