package com.example.juzzics.features.musics.ui.vm.logics

import android.app.Application
import android.content.ContentUris
import android.media.MediaPlayer
import android.provider.MediaStore
import com.example.juzzics.common.base.extensions.andWith
import com.example.juzzics.common.base.viewModel.ViewModelsLogic
import com.example.juzzics.features.musics.ui.MusicVM
import com.example.juzzics.features.musics.ui.model.MusicFileUi


class PlayMusicLogic : ViewModelsLogic {
    operator fun invoke(vm: MusicVM, musicFile: MusicFileUi?, context: Application) {
        vm.andWith(MusicVM) {
            val mediaPlayer = MEDIA_PLAYER.state<MediaPlayer>()
            val musicList = MUSIC_LIST.state<List<MusicFileUi>>()
            val clickedMusic = CLICKED_MUSIC.state<MusicFileUi>()
            fun onSameMusicCLicked() {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer.pause()
                    IS_PLAYING.setValue(false)
                } else {
                    mediaPlayer?.start()
                    IS_PLAYING.setValue(true)
                }
            }

            fun onNewMusicClicked() {
                musicFile?.let {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    MediaPlayer.create(
                        context, ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicFile.id
                        )
                    ) saveIn MEDIA_PLAYER
                    CLICKED_MUSIC.setValue(musicFile)
                    musicList?.map {
                        if (it != musicFile) it.copy(isPlaying = false)
                        else it.copy(isPlaying = true)
                    } saveIn MUSIC_LIST
                    IS_PLAYING.setValue(true)
                    MEDIA_PLAYER.state<MediaPlayer>()?.apply {
                        setOnCompletionListener { onAction(MusicVM.PlayNextAction) }
                        start()
                    }
                }
            }
            if (musicFile == clickedMusic) {
                onSameMusicCLicked()
            } else {
                onNewMusicClicked()
            }
        }
    }
}