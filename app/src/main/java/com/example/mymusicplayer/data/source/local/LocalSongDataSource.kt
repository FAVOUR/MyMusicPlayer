package com.example.mymusicplayer.data.source.local

import com.example.mymusicplayer.data.source.local.entity.Song
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.InputStream

interface LocalSongDataSource {
    fun fetchSongs(): Single<Song>
    fun saveAudioToDeviceAndStorePath(song: Song, inputStream: InputStream): String
    fun storeSongInPhone(
        input: InputStream,
        fileName: String,
        folderName: String = "My Music player",
    ): String

    fun fileName(url: String): String
    fun getFilePath(fileName: String, folderName: String): String
    fun storeSongInDb(songToStore: Song): Maybe<Long>
    fun obtainSongByTitle(titleOfSong: String): Single<Song>
    fun setAsFavourite(song: Song): Maybe<Long>
}