package com.example.juzzics.features.musics.data.localProvider

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.juzzics.features.musics.domain.model.MusicFileModel
import kotlinx.coroutines.delay

class MusicLocalProviderImpl(private val context: Context) : MusicLocalProvider {
    override suspend fun getAllLocalMusicFiles() = runCatching {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = 1"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
        )
        val cursor = contentResolver.query(uri, projection, selection, null, null)
        val musicFiles = mutableListOf<MusicFileModel>()
        while (cursor?.moveToNext() == true) {
            val id = cursor.getLong(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val album = cursor.getString(3)
            val duration = cursor.getLong(4)
            val albumId = cursor.getLong(5)

            val iconUri = ContentUris.withAppendedId(
                Uri.parse("content://media/external/audio/albumart"), albumId
            )

            musicFiles.add(MusicFileModel(id, title, artist, album, duration, iconUri))
        }
        cursor?.close()
        musicFiles.sortedBy { it.title }
    }
}