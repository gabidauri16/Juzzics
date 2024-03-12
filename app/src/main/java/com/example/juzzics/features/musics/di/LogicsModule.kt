package com.example.juzzics.features.musics.di

import com.example.juzzics.features.musics.ui.vm.logics.PlayMusicLogic
import com.example.juzzics.features.musics.ui.vm.logics.PlayNextOrPrevLogic
import org.koin.dsl.module

val logicsModule = module {
    factory { PlayMusicLogic() }
    factory { PlayNextOrPrevLogic() }
}