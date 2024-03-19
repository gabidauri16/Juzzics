package com.example.juzzics.features.musics.ui.vm.logics

import android.app.Application
import android.content.ContentUris
import android.media.MediaPlayer
import android.provider.MediaStore
import com.example.juzzics.common.base.extensions.andWith
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import com.example.juzzics.features.musics.ui.vm.MusicVM
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun MusicVM.playMusicLogic(musicFile: MusicFileUi?, context: Application) {
    this.andWith(MusicVM) {
        val mediaPlayer = MEDIA_PLAYER.state<MediaPlayer>()
        val musicList = MUSIC_LIST.state<ImmutableList<MusicFileUi>>()
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
                }?.toImmutableList() saveIn MUSIC_LIST
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