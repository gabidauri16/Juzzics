package com.example.juzzics.musics.data.localProvider

import android.content.Context
import android.provider.MediaStore
import com.example.juzzics.musics.domain.model.MusicFileModel

class MusicLocalProviderImpl(private val context: Context) : MusicLocalProvider {
    override suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>> {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = 1"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
        )
        val cursor = contentResolver.query(uri, projection, selection, null, null)
        val musicFiles = mutableListOf<MusicFileModel>()
        while (cursor?.moveToNext() == true) {
            val id = cursor.getLong(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val album = cursor.getString(3)
            val duration = cursor.getLong(4)
            musicFiles.add(MusicFileModel(id, title, artist, album, duration))
        }
        cursor?.close()
        return Result.success(musicFiles.sortedBy { it.title })
    }
}